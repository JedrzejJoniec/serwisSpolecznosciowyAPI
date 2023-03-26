# Derived from official mysql image (our base image)
FROM rabbitmq:3-management

# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./definitions.json /opt/definitions.json
COPY ./rabbitmq.conf/ /etc/rabbitmq/rabbitmq.config