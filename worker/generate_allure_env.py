import json
import os  

with open('worker/allure-results/sonarqube-metrics.json', 'r') as f:
    data = json.load(f)

measures = data['component']['measures']
metric_map = {m['metric']: m['value'] for m in measures}

sonar_host_url = os.getenv("SONAR_HOST_URL", "").rstrip("/")
project_key = "voting-app"
sonar_env = os.getenv("SONARQUBE_ENV", "SonarQube")

if sonar_host_url:
    sonar_dashboard_url = f"{sonar_host_url}/dashboard?id={project_key}&codeScope=overall"
else:
    sonar_dashboard_url = f"https://172.30.117.227:9000/dashboard?id={project_key}&codeScope=overall"

url_map = {
    "bugs": f"{sonar_host_url}/project/issues?id={project_key}&types=BUG",
    "vulnerabilities": f"{sonar_host_url}/project/issues?id={project_key}&types=VULNERABILITY",
    "code_smells": f"{sonar_host_url}/project/issues?id={project_key}&types=CODE_SMELL",
    "coverage": f"{sonar_host_url}/component_measures?id={project_key}&metric=coverage",
    "duplicated_lines_density": f"{sonar_host_url}/component_measures?id={project_key}&metric=duplicated_lines_density"
}

with open('worker/allure-results/environment.properties', 'w') as env_file:

    env_file.write(f"SonarQube_Dashboard={sonar_dashboard_url}\n")
    for measure in measures:
        metric = measure.get('metric')
        value = measure.get('value', 'N/A')

        # Create clickable HTML link if mapping exists
        env_file.write(f"{metric}={value}\n")
       # if metric in url_map:
        #    link = f"{url_map[metric]}"
         #   env_file.write(f"{metric}={value} ({link})\n")
       # else:
           #  env_file.write(f"{metric}={value}\n")
   
    
    
        
