import csv
import json

trainer_file = '../train-balanced.csv'
input_file = '../comments.json'
output_file = '../Corpora/classified_comments.json'

dict = {}
classification = {
    "text": [],
    "sarcastic": []
}

with open(trainer_file, 'r', encoding='utf-8') as data:
    training_data = csv.reader(data)
    for line in training_data:
        text = line[0].split('|')
        authors = text[1].split()
        sarcastic = text[2].split()
        dict[authors[0]] = sarcastic[0]
        dict[authors[1]] = sarcastic[1]

def get_classified_comments(input_file, id):
    with open(input_file, 'r', encoding='utf-8') as file:
        for line in file:
            item = json.loads(line.strip())
            if id in item:
                classification['text'].append(item[id].get('text'))
                classification['sarcastic'].append(dict[id])
                break

number = 0
short_comments = list(dict.keys())[2001:4000]
for comment in short_comments:
    get_classified_comments(input_file, comment)
    number += 1
    print(number)

with open(output_file, 'w', encoding='utf-8') as result:
    json.dump(classification, result, indent=4)