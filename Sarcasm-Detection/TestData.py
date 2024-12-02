import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification

model = AutoModelForSequenceClassification.from_pretrained("./saved-pretrain")
tokenizer = AutoTokenizer.from_pretrained("./saved-pretrain")

comments = [
    "Oh, I just love how you always save your best work for the last minute—keeps the adrenaline pumping for the rest of us.",
    "Your ability to turn a simple task into a three-day adventure is truly inspiring.",
    "I’m amazed at how you always manage to be ‘busy’ right when we need you the most.",
    "Thanks for sharing your groundbreaking insight that coffee helps productivity—no one could have figured that out!",
    "Your sense of timing is impeccable—meetings wouldn’t feel the same without your ‘just-in-time’ arrivals.",
    "It’s great how you always remind us that perfection isn’t everything… by setting such a relatable example.",
    "I really admire your dedication to making every email as long as a novel—brevity is so overrated!",
    "Your multitasking skills are legendary—especially when it comes to mixing work with scrolling social media.",
    "Truly commendable how you always find the silver lining in missing deadlines.",
    "Your PowerPoint slides are a work of art—especially the ones we get to see for the first time during the presentation.",
    "Your ability to stay calm under pressure really helps the whole team stay grounded.",
    "I appreciate how you’re always willing to help out when someone’s stuck.",
    "Your attention to detail in our reports has made a big difference in our accuracy.",
    "You have a great ability to think creatively and suggest innovative solutions.",
    "I really admire the way you handle difficult conversations with professionalism and empathy.",
    "Your dedication to meeting deadlines is something the entire team appreciates.",
    "Thank you for always bringing positive energy to the office—it really lifts everyone’s spirits.",
    "You communicate your ideas so clearly, which helps us all stay on the same page.",
    "Your ability to mentor and support newer team members is truly valuable.",
    "I’ve noticed how you consistently go above and beyond to deliver high-quality work."
]

tokenized_comments = tokenizer(comments, return_tensors="pt", truncation=True, padding=True, max_length=512)

model.eval()
with torch.no_grad():
    results = model(**tokenized_comments)

resulted_logits = results.logits
probabilities = torch.nn.functional.softmax(resulted_logits, dim=-1)
predicted_classes = torch.argmax(probabilities, dim=-1).numpy()

for text, predicted_class, prob in zip(comments, predicted_classes, probabilities):
    response = "sarcastic" if predicted_class == 1 else "not sarcastic"
    print(f"Text: {text} Prediction: {response} (Prob: {prob[predicted_class].item():.2f})")