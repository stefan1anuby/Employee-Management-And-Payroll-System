import json

input_file = "../Corpora/Sarcasm_Headlines_Dataset.json"
output_file = "../Corpora/Structured_Sarcasm_Headlines_Dataset.json"

structured_data = {
    "text": [],
    "is_sarcastic": [],
    "article_link": []
}

with open(input_file, "r", encoding="utf-8") as shd:
    for line in shd:
        try:
            object = json.loads(line.strip())
            structured_data["text"].append(object.get("headline"))
            structured_data["is_sarcastic"].append(object.get("is_sarcastic"))
            structured_data["article_link"].append(object.get("article_link"))
        except json.JSONDecodeError:
            print(f"Invalid JSON line: {line}")

with open(output_file, "w", encoding="utf-8") as new_shd:
    json.dump(structured_data, new_shd, indent=4)

print(f"Structured data saved to {output_file}")