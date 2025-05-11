import nltk
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer # lemmatize
import string

import tkinter as tk
from tkinter import scrolledtext


# All the key-value pairs must be lemmatized
data = {
    "timing":["hours", "available", "timing"],
    "facility":["x-ray","ctscan","sonography","mri"],
    "canteen":["food", "eat","canteen"]
}

responses = {
    "timing":"We are available 24x7",
    "facility":"Yes it is available",
    "canteen":"We are having good canteen service",
    "default":"Sorry I am unable to answer it."
}


def preprocess(user_input):

    user_input= user_input.lower()
    tokens = word_tokenize(user_input)

    stop = stopwords.words('english')
    removed_stop = []
    for t in tokens:
        if t not in stop:
            removed_stop.append(t)


    removed_punc = []
    for w in removed_stop:
        if w not in string.punctuation:
            removed_punc.append(w)

    preprocessed = []
    lem = WordNetLemmatizer()
    for w in removed_punc:
        preprocessed.append(lem.lemmatize(w))
    
    return preprocessed

def respond():
    user = user_input.get()
    preprocessed_tokens = preprocess(user)

    found = False

    for word in preprocessed_tokens:
        for key, val in data.items():
            if word in val:
                bot = responses[key]
                found = True
                break
        if found:
            break

    if not found:
        bot = responses["default"]

    chat_window.insert(tk.END, "You: " + user + "\n\n" )
    chat_window.insert(tk.END, "Bot: " + bot + "\n\n" )

# UI must be after logic
root = tk.Tk()
root.title("chatbot")

chat_window = scrolledtext.ScrolledText(root, width=40, height=20, font=('Arial', 12), wrap=tk.WORD)
chat_window.pack(padx=10, pady=10)
chat_window.insert(tk.END, "How can I help?\n\n")
chat_window.config(state="normal")

user_input = tk.Entry(root, width=40, font=('Arial', 12))
user_input.pack(padx=10, pady=10)
user_input.bind("<Return>", lambda event: respond())

button = tk.Button(root, text="Send", command=respond) # pass root in every component
button.pack(padx=10, pady=10) # pack each and every component after declaration

root.mainloop()
