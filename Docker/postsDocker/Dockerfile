# Derived from official mysql image (our base image)
FROM mysql
# Add a database
ENV MYSQL_DATABASE posts
# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup
COPY ./serwisbaza.sql/ /docker-entrypoint-initdb.d/serwisbaza.sql