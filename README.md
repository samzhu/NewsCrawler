# NewsCrawler
Web crawler For News

## get news from web
```
curl --request GET \
  --url 'http://localhost:8080/api/v1/newscrawler?datestart=20150701&dateend=20150702' \
  --header 'content-type: application/json'
```

## get news from es
```
curl --request GET \
  --url http://localhost:8080/api/v1/news \
  --header 'content-type: application/json'
  ```
