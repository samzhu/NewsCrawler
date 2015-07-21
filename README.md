# NewsCrawler
Web crawler For News

## get news from web
```
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" 'http://localhost:8080/api/v1/newscrawler?datestart=20150701&dateend=20150702'
```

## get news from es
```
curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" 'http://localhost:8080/api/v1/news'
```
