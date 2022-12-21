gcloud functions deploy snapswapmock --source . --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http
YOUR_HTTP_TRIGGER_URL=$(gcloud functions describe snapswapmock --format='value(httpsTrigger.url)')
curl -w "\n" $YOUR_HTTP_TRIGGER_URL/snapswap/mock/api/v1/stats 
