#!/bin/bash

BASE_URL="http://localhost:8080/api"

echo "=== Creating 10 Users ==="
USER_IDS=()
for i in $(seq 1 10); do
  RESPONSE=$(curl -s -X POST "$BASE_URL/users" \
    -H "Content-Type: application/json" \
    -d "{\"name\": \"User $i\", \"email\": \"user${i}@test.com\"}")
  
  USER_ID=$(echo "$RESPONSE" | grep -o '"id":"[^"]*"' | head -1 | cut -d'"' -f4)
  USER_IDS+=("$USER_ID")
  echo "Created User $i: $USER_ID"
done

echo ""
echo "=== Creating 5 Projects ==="
PROJECT_IDS=()
STATUSES=("PLANNING" "ACTIVE" "COMPLETED" "ON_HOLD" "ARCHIVED")
for i in $(seq 1 5); do
  RESPONSE=$(curl -s -X POST "$BASE_URL/projects" \
    -H "Content-Type: application/json" \
    -d "{\"name\": \"Project $i\", \"description\": \"Description for project $i\"}")
  
  PROJECT_ID=$(echo "$RESPONSE" | grep -o '"id":"[^"]*"' | head -1 | cut -d'"' -f4)
  PROJECT_IDS+=("$PROJECT_ID")
  echo "Created Project $i: $PROJECT_ID"
done

echo ""
echo "=== Creating 100 Tasks ==="
TASK_STATUSES=("TODO" "IN_PROGRESS" "COMPLETED" "BACKLOG")
for i in $(seq 1 100); do
  # Pick random user and project
  USER_INDEX=$((RANDOM % 10))
  PROJECT_INDEX=$((RANDOM % 5))
  STATUS_INDEX=$((RANDOM % 4))
  
  ASSIGNED_USER="${USER_IDS[$USER_INDEX]}"
  PROJECT="${PROJECT_IDS[$PROJECT_INDEX]}"

  curl -s -X POST "$BASE_URL/tasks" \
    -H "Content-Type: application/json" \
    -d "{
      \"title\": \"Task $i\",
      \"description\": \"Description for task $i\",
      \"projectId\": \"$PROJECT\",
      \"assignedToId\": \"$ASSIGNED_USER\"
    }" > /dev/null

  echo "Created Task $i"
done

echo ""
echo "=== Done ==="
echo "Users: ${#USER_IDS[@]}"
echo "Projects: ${#PROJECT_IDS[@]}"
echo "Tasks: 100"