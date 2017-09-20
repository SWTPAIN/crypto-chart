# crypto-chart

A toy project for learning [re-frame](https://github.com/Day8/re-frame) and clojurescript. It will be about crypto currency charting

## Development Mode

### Run application:

```
lein sass4clj once
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
