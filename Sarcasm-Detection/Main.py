import pandas as pd
from datasets import load_dataset
from transformers import BertTokenizer, BertForSequenceClassification, TrainingArguments, Trainer, pipeline

data = {
    "text": [
        "Oh, look, it's the office’s one-person vacation planner. Always 'too busy'—to work.",
        "Who needs Google when we have their infinite wisdom... in everything except their actual job?",
        "A true trailblazer—turning coffee breaks into a full-fledged art form.",
        "Their PowerPoint slides? A masterpiece in minimalism. Truly inspiring blank spaces.",
        "Wow, I didn’t know you could master the art of ‘delegating’ to the rest of us so quickly!",
        "Ah, another meeting where your groundbreaking insights will surely revolutionize... absolutely nothing. Can’t wait!",
        "This meeting is super productive!",
        "They're the first person I'd call for problem-solving; they always bring fresh ideas to the table.",
        "Their dedication to making every detail perfect inspires everyone around them.",
        "A real team player who steps up when the team needs support the most.",
        "They have a knack for turning even the most mundane tasks into something innovative.",
        "Their positive energy is contagious, and it makes a huge difference in our workplace."
    ],
    "label": [1,1,1,1,1,1,0,0,0,0,0,0]
}

#transform the data into a csv file
df = pd.DataFrame(data)
df.to_csv("sarcasm_dataset.csv", index=False)

#load the corpus and a pretrained tokenizer
dataset = load_dataset("csv", data_files="sarcasm_dataset.csv")
tokenizer = BertTokenizer.from_pretrained("bert-base-uncased")

#tokenize the data in order to be processed by BART
def tokenize_function(examples):
    return tokenizer(examples["text"], padding="max_length", truncation=True)

tokenized_datasets = dataset.map(tokenize_function, batched=True)

model = BertForSequenceClassification.from_pretrained("bert-base-uncased", num_labels=2)

tokenized_datasets = tokenized_datasets["train"].train_test_split(test_size=0.2)

#set the training arguments
training_args = TrainingArguments(
    output_dir="./results",
    eval_strategy="epoch",
    learning_rate=2e-5,
    per_device_train_batch_size=16,
    per_device_eval_batch_size=16,
    num_train_epochs=4,
    weight_decay=0.01
)

trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=tokenized_datasets["train"],
    eval_dataset=tokenized_datasets["test"],
    processing_class=tokenizer
)

#train and evaluate the data
trainer.train()

trainer.evaluate()

#save the model and the tokenizer for future use
model.save_pretrained("./sarcasm-detector")
tokenizer.save_pretrained("./sarcasm-detector")

#test the sentiment analysis on an example
sarcasm_detector = pipeline("text-classification", model="./sarcasm-detector", tokenizer="./sarcasm-detector")
comments = [
    "I really appreciate how you always take the time to help out the team. Your support makes a big difference.",
    "Wow, it's amazing how you always manage to volunteer for the easy tasks. Truly a master strategist!"
]
predictions = sarcasm_detector(comments)
label_mapping = {"LABEL_0": "Non-Sarcastic", "LABEL_1": "Sarcastic"}
for comment, prediction in zip(comments, predictions):
    label = label_mapping[prediction['label']]
    score = prediction['score']
    print(f"Comment: {comment}\nPredicted label: {label}, Confidence: {score:.4f}\n")
