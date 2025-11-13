import json
import os  

with open('/var/lib/jenkins/workspace/Allure_TestReport/worker/allure-results/sonarqube-metrics.json', 'r') as f:
    data = json.load(f)

measures = data['component']['measures']

sonar_host_url = os.getenv("SONAR_HOST_URL", "").rstrip("/")
project_key = "voting-app"
sonar_env = os.getenv("SONARQUBE_ENV", "SonarQube")

if sonar_host_url:
    sonar_dashboard_url = f"{sonar_host_url}/dashboard?id={project_key}"
else:
    sonar_dashboard_url = f"https://172.30.117.227:9000/dashboard?id={project_key}"

with open('/var/lib/jenkins/workspace/Allure_TestReport/worker/allure-results/environment.properties', 'w') as env_file:
    for measure in measures:
        # Format key=value for the properties file
        env_file.write(f"{measure['metric']}={measure['value']}\n")
        env_file.write(f"SonarQube_Dashboard={sonar_dashboard_url}\n")
        
