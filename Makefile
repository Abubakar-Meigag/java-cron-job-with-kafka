.PHONY: build up down logs restart

build:
	docker compose build

up:
	docker compose up --build -d

down:
	docker compose down

logs:
	docker compose logs -f scheduler consumer-a consumer-b

restart: down up




#   make up       # build and start everything
#   make down     # stop everything
#   make logs     # watch scheduler, consumer-a, consumer-b logs
#   make restart  # stop then start fresh
#   make build    # build images only