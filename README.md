# Scala Zio and Quill example

In keeping with my ongoing functional language quest, I had to check out 
[Scala 3](https://www.scala-lang.org/download/scala3.html), [ZIO](https://zio.dev/), 
[ZIO http](https://github.com/dream11/zio-http) and [Quill](https://github.com/zio/zio-protoquill) 
with their collective ability to do functional effects. ZIO http is the fastest Scala 
HTTP server framework, partially because of the efficiency of ZIO and its fibre architecture.

While this is a rather new ecosystem, ZIO and ZIO http are in production and operating at 
scale at [Dream 11](https://www.dream11.com/) and others so it can be taken fairly 
seriously.

The build requires only that [sbt](https://www.scala-sbt.org/) be installed and that a working
instance of PostgreSQL be available. First, set up the database:

```sh
createdb dbwriter_scala
createuser -P test # Add a password "test" at the prompt 
```
Now use `psql -d dbwriter_scala` to create a test table:
```sql
CREATE TABLE sample (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL, 
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    v0 REAL, 
    v1 REAL );
```

## The good
- Functional effects can do everything async/await patterns can do, but feel more 
  elegant thanks to Scala syntactic sugar. Error management and concurrency nuances 
  are easier and effects compose flawlessly.
- Type safety is baked into everything, so you cannot mess things up. The compiler and 
  IDE catch errors quickly and accurately give or take how much Scala 3 they can handle.
- Scala 3 and ZIO can do huge tasks with little code.
- Zlayer dependency injection is one of the best mechanisms I have seen for managing
  things like database connections, authenticators and the like.

## Not so good
- Functional effects are strange to imperative programmers so the learning curve is steep.
- Documentation systems such as Swagger are only available through Tapir and there
  is no real way to generate these docs with Zio HTTP.
- Macros are just amazing in their ability to make boilerplate go away but they make
  it very difficult for the IDE to figure out what is going on. I depend heavily on
  the IDE to help infer types and find imports and both Rust and Scala IDEs struggle.
- There is no real database migration strategy for Quill like Alembic, Diesel or Slick. 
  You can cobble something together but it is a bit primitive for my taste. 

## The ugly
- This is a bleeding edge world. Scala 3 is new and ZIO 2.0 is very much RC.
- ZIO is elegant, fast and powerful but the documentation is poor and is hard to get 
  going. I had to rely on the Discord channels to find out how to do some fairly
  basic things.




