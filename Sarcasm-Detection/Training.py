import json
import pandas as pd
import torch
import SarcasmDataset
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
from datasets import load_dataset
from transformers import TrainingArguments, Trainer, AutoTokenizer, AutoModelForSequenceClassification

with open("Structured_Sarcasm_Headlines_Dataset.json", "r", encoding="utf-8") as file:
    data = json.load(file)

df = pd.DataFrame({
    "text": data["text"],
    "is_sarcastic": data["is_sarcastic"]
})

train_texts, test_texts, train_labels, test_labels = train_test_split(df["text"], df["is_sarcastic"], test_size=0.2, random_state=42)

tokenizer = AutoTokenizer.from_pretrained("bert-base-uncased")
train_encodings = tokenizer(list(train_texts), truncation=True, padding=True, max_length=128, return_tensors="pt")
test_encodings = tokenizer(list(test_texts), truncation=True, padding=True, max_length=128, return_tensors="pt")

train_labels = torch.tensor(list(train_labels))
test_labels = torch.tensor(list(test_labels))

train_dataset = SarcasmDataset.SarcasmDataset(train_encodings, train_labels)
test_dataset = SarcasmDataset.SarcasmDataset(test_encodings, test_labels)

model = AutoModelForSequenceClassification.from_pretrained("bert-base-uncased", num_labels=2)

# #set the training arguments
training_args = TrainingArguments(
    output_dir="./results",
    eval_strategy="epoch",
    save_strategy="epoch",
    logging_dir="./logs",
    learning_rate=2e-5,
    per_device_train_batch_size=16,
    per_device_eval_batch_size=16,
    num_train_epochs=4,
    weight_decay=0.01,
    logging_steps=10
)

trainer = Trainer(
    model=model,
    args=training_args,
    train_dataset=train_dataset,
    eval_dataset=test_dataset
)

#train and evaluate the data
trainer.train()

#save the model and the tokenizer for future use
model.save_pretrained("./saved-pretrain")
tokenizer.save_pretrained("./saved-pretrain")