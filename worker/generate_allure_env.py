import json

with open('allure-results/sonarqube-metrics.json', 'r') as f:
    data = json.load(f)

measures = data['component']['measures']
with open('allure-results/environment.properties', 'w') as env_file:
    for measure in measures:
        # Format key=value for the properties file
        env_file.write(f"{measure['metric']}={measure['value']}\n")
