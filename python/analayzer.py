import spacy

nlp = spacy.load("en_core_web_sm")

def extract_keywords(text):
    doc = nlp(text)
    return set([token.lemma_.lower() for token in doc if token.is_alpha and not token.is_stop])

def analyze_resume(resume_text, jd_text):
    resume_keywords = extract_keywords(resume_text)
    jd_keywords = extract_keywords(jd_text)

    matched = resume_keywords.intersection(jd_keywords)
    missing = jd_keywords - resume_keywords

    match_percent = round(len(matched) / len(jd_keywords) * 100) if jd_keywords else 0

    return {
        "match_percent": match_percent,
        "missing_keywords": list(missing)
    }
