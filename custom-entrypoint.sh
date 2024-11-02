#!/bin/bash
set -e

# Call original entrypoint script
docker-entrypoint.sh postgres &

pid="$!"

# Wait for PostgreSQL to become available
until pg_isready -q -h localhost -U "postgres"; do
  echo 'Waiting for PostgreSQL to become available'
  sleep 1

done

# Run your SQL script
echo 'Trying to run the SQL script'
if psql -v ON_ERROR_STOP=1 --username "postgres" --dbname "EMPS" < /docker-entrypoint-initdb.d/db_setup.sql; then
	 echo "db_setup.sql executed successfully."
else
    echo "An error occurred while executing db_setup.sql."
    exit 1
fi

echo 'Wit for PostgreSQL process to end'
# Wait for PostgreSQL process to end
wait "$pid"