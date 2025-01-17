import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification
from sklearn.metrics import precision_score, recall_score, f1_score, accuracy_score

model = AutoModelForSequenceClassification.from_pretrained("../saved-fifth-pretrain")
tokenizer = AutoTokenizer.from_pretrained("../saved-fifth-pretrain")

sarcastic_comments = [
    "Oh great, another meeting about meetings. Just what we needed.",
    "Can’t wait to be inspired by another email about hitting unrealistic targets.",
    "Thrilled to get more tasks labeled as 'urgent' that could have waited till next month.",
    "Absolutely love how the solution to low morale is a motivational poster.",
    "Looking forward to my next performance review where I’ll learn I need to smile more.",
    "It’s so inspiring how ‘team collaboration’ really just means ‘do it yourself.’",
    "Nothing like a ‘work from anywhere’ policy that comes with a 24/7 on-call expectation.",
    "Excited for the next brainstorming session that produces exactly zero actionable ideas.",
    "Love how leadership keeps saying they’re ‘listening,’ yet nothing changes.",
    "Can’t wait for another ‘team-building exercise’ that feels more like a punishment.",
    "Absolutely thrilled to receive my annual bonus of a coffee mug.",
    "Looking forward to seeing how our ‘dynamic strategy’ changes for the fifth time this week.",
    "So thankful for the constant feedback that feels more like micromanagement.",
    "Excited to read another inspirational quote from someone who’s never worked here.",
    "Nothing like the thrill of working overtime for ‘the experience.’",
    "Can’t wait for the next email that starts with ‘Due to unforeseen circumstances…’",
    "Really love how our office’s ‘open-door policy’ only applies when it’s convenient.",
    "Excited to get my hands on more cutting-edge software that’s already outdated.",
    "Looking forward to hearing about how we’re all a ‘family’ right before layoffs start.",
    "Nothing says gratitude like an appreciation lunch with cold sandwiches.",
    "Love how the reward for exceeding expectations is more expectations.",
    "Can’t wait to see how this year’s training will recycle last year’s slides.",
    "Thrilled to learn that ‘urgent’ tasks are still waiting for approval three weeks later.",
    "Excited for the upcoming restructuring that will just reshuffle the same problems.",
    "Love how the ‘flexible working hours’ means I’m always online.",
    "Looking forward to the next email chain that could’ve been a 30-second call.",
    "Oh great, another initiative to improve morale that ignores employee feedback.",
    "Can’t wait to hear about how our ‘values’ are driving decisions that make no sense.",
    "Nothing says progress like endless PowerPoint presentations with no follow-up.",
    "Thrilled to be part of a company that innovates by renaming old practices.",
    "So happy to see the workload double without any increase in resources.",
    "Love how the solution to burnout is ‘working smarter, not harder,’ whatever that means.",
    "Excited to participate in another focus group that will be promptly ignored.",
    "Really appreciate how ‘feedback sessions’ are just one-sided lectures.",
    "Can’t wait for another week of doing other departments’ work for no credit.",
    "Thrilled to see how our ‘cost-cutting measures’ somehow only affect employees.",
    "Looking forward to the next motivational talk that sidesteps every real issue.",
    "Love how the ‘new normal’ just means normal, but with more buzzwords.",
    "Excited for another strategic meeting where the only plan is ‘work harder.’",
    "Nothing like hearing about record profits while salaries stay the same.",
    "Can’t wait to be part of another ‘pilot program’ that never takes off.",
    "Love how every problem becomes my responsibility by default.",
    "Thrilled to get more emails marked ‘high priority’ that aren’t relevant to me.",
    "Nothing says teamwork like delegating all the hard parts to one person.",
    "Excited to hear more about how we’re ‘breaking down silos’ by adding more bureaucracy.",
    "Looking forward to another policy that’s designed to solve problems no one has.",
    "Love how the ‘feedback loop’ is just a circle of unhelpful comments.",
    "Can’t wait to be asked for ‘quick results’ on projects that take months.",
    "Thrilled to see how the ‘streamlined process’ is actually more complicated.",
    "Excited for another meeting where nothing is resolved but everyone nods in agreement.",
    "So exciting to watch the budget shrink while the expectations grow.",
    "Thrilled that our ‘fast-paced environment’ just means constant chaos.",
    "Can’t wait to hear how this next ‘cost optimization’ will benefit everyone but us.",
    "Really appreciate how 'team accountability' means I do all the work.",
    "Love how ‘working smarter’ always translates to ‘working longer.’",
    "Excited to see yet another email chain with no actual decisions being made.",
    "Looking forward to the ‘team effort’ that ends with one person doing everything.",
    "Amazing how ‘streamlined processes’ require triple the paperwork.",
    "Absolutely love it when last-minute changes become my problem.",
    "Nothing says leadership like taking credit for the team's hard work.",
    "Thrilled to attend another mandatory meeting where nothing relevant gets discussed.",
    "Love how ‘agile development’ means changing direction every other day.",
    "Can’t wait for my reward of ‘experience’ instead of a raise.",
    "So grateful for the opportunity to solve problems no one else wanted to address.",
    "Excited to see how this new policy will complicate things further.",
    "Appreciating how overtime is ‘optional,’ but deadlines aren’t.",
    "Looking forward to receiving vague feedback that helps in no way whatsoever.",
    "So inspiring to hear about the company’s vision from people who never implement it.",
    "Really love how we ‘celebrate diversity’ by ignoring diverse opinions.",
    "Grateful for the opportunity to mentor someone who will replace me for half the pay.",
    "Excited to work on another project that gets shelved before completion.",
    "Love how every ‘improvement’ adds another step to the process.",
    "Nothing says urgency like sending an email and then walking over to ask if I read it.",
    "Excited to implement another ‘game-changing’ tool that breaks half the systems.",
    "Love how every new system comes with hours of training but no actual support.",
    "Can’t wait for my next big career move to another department’s workload.",
    "Appreciating how my ‘performance bonus’ is a pat on the back.",
    "Thrilled to spend more time creating reports than solving problems.",
    "Nothing says innovation like recycling last year's failed ideas.",
    "Excited to learn how my role has expanded without a title change.",
    "Love the new office design that prioritizes aesthetics over functionality.",
    "Can’t wait to hear about how important we are, right before more budget cuts.",
    "Amazing how the ‘open-door policy’ always leads to a closed email thread.",
    "Excited to be part of the team that’s ‘leading from behind.’",
    "Grateful for the chance to solve the same problem for the fifth time this month.",
    "Thrilled to get a front-row seat to the 'we value employees' speech during layoffs.",
    "Nothing inspires loyalty like a ‘fun committee’ replacing actual benefits.",
    "Excited for the next quarter’s goals that are set without any input from the team.",
    "Love how collaboration means copying and pasting someone else’s ideas.",
    "Really appreciate the surprise deadlines that appear out of nowhere.",
    "Looking forward to hearing how a ‘customer-first approach’ justifies more work for us.",
    "Can’t wait for another software update that fixes none of the issues we have.",
    "Thrilled to learn that our company’s future depends entirely on ‘stretch goals.’",
    "Love how ‘continuous improvement’ means we’re always starting over.",
    "So excited to see which policy will contradict itself this time.",
    "Nothing beats the thrill of working hard for recognition that never comes.",
    "Excited for the new initiative that’s just a rebranded version of the old one.",
    "Really appreciate how my hard work turns into someone else’s promotion."
]

non_sarcastic_comments = [
    "Grateful for the mentorship opportunities available within the company.",
    "Excited to contribute to projects that make a meaningful impact.",
    "Appreciating the clarity of communication from leadership about our goals.",
    "Looking forward to learning new skills through the company’s training programs.",
    "Proud to work with such a talented and innovative team.",
    "Grateful for the flexibility to balance my personal and professional life.",
    "Excited about the company’s commitment to sustainable practices.",
    "Appreciating the collaborative spirit across different departments.",
    "Looking forward to implementing new ideas that enhance productivity.",
    "Thankful for the recognition and encouragement from my peers.",
    "Proud of the company’s focus on diversity and inclusion in the workplace.",
    "Excited to contribute to the company’s mission and vision for the future.",
    "Looking forward to our upcoming team-building activities to strengthen bonds.",
    "Proud of how our team navigates challenges with resilience and creativity.",
    "Appreciating the company’s investment in employee well-being and growth.",
    "Excited about the opportunities to take on more leadership responsibilities.",
    "Thankful for the strong support system within our work environment.",
    "Proud to see our company leading the way in industry innovation.",
    "Grateful for the constructive feedback that helps me improve every day.",
    "Looking forward to collaborating with colleagues on exciting new projects.",
    "Appreciating the emphasis on ethical practices in our business decisions.",
    "Excited to celebrate our team’s achievements and milestones.",
    "Thankful for the company’s commitment to creating a positive workplace culture.",
    "Proud to be part of an organization that values continuous improvement.",
    "Grateful for the sense of purpose that my role brings to my daily work.",
    "Looking forward to achieving new goals and setting higher standards.",
    "Appreciating the open communication that fosters trust within our team.",
    "Excited to grow professionally in an environment that values learning and development.",
    "Thankful for the strong leadership that inspires us to perform at our best.",
    "Proud of how our team embraces change and adapts to new challenges.",
    "Grateful for the opportunities to make a meaningful contribution to the company.",
    "Looking forward to the positive impact our efforts will have on our customers.",
    "Appreciating the teamwork and collaboration that drive our success.",
    "Excited to innovate and deliver value to our clients in unique ways.",
    "Thankful for the friendships and connections I’ve built in this workplace.",
    "Proud to see how our company supports the community through various initiatives.",
    "Grateful for the company’s dedication to fostering a growth-oriented culture.",
    "Looking forward to continuing to build strong relationships with our partners.",
    "Appreciating the autonomy given to us to take ownership of our projects.",
    "Grateful to be part of a company that values employee growth.",
    "Excited to work on projects that challenge my skills and creativity.",
    "Proud of the progress our team has made this quarter.",
    "Thankful for a leadership team that prioritizes clear communication.",
    "Looking forward to learning more through our professional development programs.",
    "Appreciating the collaborative environment that encourages innovation.",
    "Proud to contribute to a company that makes a difference in our community.",
    "Grateful for the trust and autonomy given to me in my role.",
    "Excited to see how our efforts are driving measurable results.",
    "Appreciating the positive energy and support from my coworkers.",
    "Looking forward to achieving great things together as a team.",
    "Thankful for the company’s commitment to sustainability and ethical practices.",
    "Proud to work in an environment that fosters respect and inclusion.",
    "Excited about the opportunities to lead initiatives that have a real impact.",
    "Grateful for the mentorship and guidance I’ve received from my peers.",
    "Looking forward to the innovative changes our leadership is implementing.",
    "Appreciating how our team celebrates every win, big or small.",
    "Proud to be part of an organization that values employee feedback.",
    "Thankful for the flexibility to balance my work and personal life effectively.",
    "Excited to tackle the challenges ahead with such a supportive team.",
    "Grateful for the recognition I’ve received for my hard work.",
    "Proud of the strides we’re making in improving our customer experience.",
    "Appreciating the resources and tools provided to help us succeed.",
    "Looking forward to exploring new opportunities within the company.",
    "Thankful for the company’s focus on creating a healthy work-life balance.",
    "Excited about the creative solutions our team has developed recently.",
    "Grateful for the positive impact our work has on the community.",
    "Proud to be part of a workplace where ideas are encouraged and valued.",
    "Appreciating the dedication and resilience of my colleagues.",
    "Looking forward to taking on new challenges and responsibilities.",
    "Thankful for the consistent support from my manager and team leads.",
    "Excited to be part of a company that values integrity and transparency.",
    "Grateful for the connections I’ve made with incredible professionals.",
    "Proud of the company’s achievements and how we’ve grown together.",
    "Appreciating the learning opportunities available through various training programs.",
    "Looking forward to contributing to the company’s strategic goals.",
    "Thankful for the emphasis on innovation and staying ahead in the industry.",
    "Excited to see how our hard work will shape the company’s future.",
    "Grateful for the chance to work with such talented individuals.",
    "Proud to be part of a culture that prioritizes employee well-being.",
    "Appreciating the team spirit that makes challenging tasks enjoyable.",
    "Looking forward to celebrating our collective achievements at the year-end event.",
    "Thankful for the leadership’s vision and how it aligns with our goals.",
    "Excited to step into new opportunities for growth and advancement.",
    "Grateful for the company’s focus on long-term success and sustainability.",
    "Proud to see how we continue to innovate in a competitive market.",
    "Appreciating the opportunity to share my ideas and see them implemented.",
    "Looking forward to seeing how our projects positively impact our clients.",
    "Thankful for the collaboration and mutual respect in our workplace.",
    "Excited to continue building on the momentum we’ve achieved this year.",
    "Grateful for the diversity of perspectives that strengthen our team.",
    "Proud of how our company continues to prioritize ethical leadership.",
    "Appreciating the clarity of our goals and the support to achieve them."
]

comments = []
values = []

for sarcastic_comment in sarcastic_comments:
    comments.append(sarcastic_comment)
    values.append(1)

for non_sarcastic_comment in non_sarcastic_comments:
    comments.append(non_sarcastic_comment)
    values.append(0)

tokenized_comments = tokenizer(comments, return_tensors="pt", truncation=True, padding=True, max_length=512)

model.eval()
with torch.no_grad():
    results = model(**tokenized_comments)

resulted_logits = results.logits
probabilities = torch.nn.functional.softmax(resulted_logits, dim=-1)
predicted_classes = torch.argmax(probabilities, dim=-1).numpy()

index = 0
for text, predicted_class, prob in zip(comments, predicted_classes, probabilities):
    response = "sarcastic" if predicted_class == 1 else "not sarcastic"
    value = values[index]
    index += 1
    print(f"Text: {text} Prediction: {response} Real value: {value} (Prob: {prob[predicted_class].item():.2f})")

metrics = {
    "accuracy": accuracy_score(values, predicted_classes),
    "precision": float(precision_score(values, predicted_classes)),
    "recall": float(recall_score(values, predicted_classes)),
    "f1_score": float(f1_score(values, predicted_classes))
}
print(metrics)