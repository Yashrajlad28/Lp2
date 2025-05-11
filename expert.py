# --- Knowledge Base ---
RULES = [
    {
        "conditions": {"fever"},
        "fact":"infection",
        "explanation":"Monitor Fever. Maybe early signs of infection"
    },
    {
        "conditions": {"fatigue","headache"},
        "fact":"dehydration",
        "explanation": "Fatigue and headache together often indicate dehydration."
    },
    {
        "conditions": {"runny_nose", "sneezing"},
        "fact":"cold",
        "explanation":"Runny nose and sneezing without fever indicate cold"
    },
    {
        "conditions": {"cough", "runny_nose"},
        "fact": "cold",
        "explanation": "Cough with runny nose suggests a mild upper respiratory infection."
    },



    {
        "conditions": {"dehydration", "fever"},
        "fact": "heat_exhaustion",
        "explanation": "Fever and dehydration together may suggest overheating or heat exhaustion."
    },
    {
        "conditions": {"cold", "fever"},
        "fact":"flu",
        "explanation": "Flu often presents as a cold that escalates with fever and fatigue."
    },
    {
        "conditions": {"body_aches","fever","cough"},
        "fact":"flu",
        "explanation": "Fever, cough, and body aches together are strong indicators of influenza."
    },


    {
        "conditions": {"flu", "fatigue"},
        "fact":"severe_flu",
        "explanation": "Fatigue with flu may signal more aggressive viral activity."
    }
]

UNIQUE_CONDITIONS = set()
FACTS = set()


def ask_user_facts():
    for rule in RULES:
        for condition in rule.get("conditions"):
            UNIQUE_CONDITIONS.add(condition)

    for condition in UNIQUE_CONDITIONS:
        user_input = True if str(input(f"Do you have {' '.join(str(condition).upper().split('_'))}? (y/n) : ")).lower().strip().startswith('y') else False
        if(user_input):
            FACTS.add(condition)


def forward_chain():
    conclusions = set()
    applied_rules = set()
    flag = True

    while flag:
        flag = False
        for i, rule in enumerate(RULES):
            if i in applied_rules:
                continue
            if rule.get("conditions").issubset(FACTS):
                new_fact = rule.get("fact")
                if new_fact not in FACTS:
                    FACTS.add(new_fact)
                    conclusions.add(rule.get("explanation"))
                    applied_rules.add(i)
                    flag = True


    print("=== Possible Conclusions ===")
    for conclusion in list(conclusions):
        print(conclusion)


def main():
    ask_user_facts()
    if len(FACTS) == 0:
        print("No diagnostics available")
        return

    forward_chain()


if __name__ == '__main__':
    main()