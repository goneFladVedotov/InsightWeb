#!/bin/bash

echo "Waiting for ClickHouse to be ready..."
while ! curl -s http://clickhouse:8123 >/dev/null; do
  sleep 1
done

echo "ClickHouse is ready! Starting migration..."

for sql_file in /migrations/*.sql; do
  echo "Executing $sql_file..."
  clickhouse-client \
    --host clickhouse \
    --user default \
    --password default \
    --database default \
    --multiquery < "$sql_file"
done

echo "Migration completed!"