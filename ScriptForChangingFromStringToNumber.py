import json

input_file1 = "Corpora/classified_comments.json"
input_file2 = "Corpora/Structured_Sarcasm_Headlines_Dataset.json"
output_file = "Corpora/Structured_Corpus.json"

with open(input_file1, 'r', encoding='utf-8') as file1:
    data = json.load(file1)

with open(output_file, 'r', encoding='utf-8') as file2:
    corpus = json.load(file2)

print(len(corpus['text']))
print(len(corpus['is_sarcastic']))

for text in data['text']:
    corpus['text'].append(text)

for number in data['sarcastic']:
    corpus['is_sarcastic'].append(int(number))

# with open(output_file, 'w', encoding='utf-8') as result:
#     json.dump(corpus, result, indent=4)

print(len(corpus['text']))
print(len(corpus['is_sarcastic']))