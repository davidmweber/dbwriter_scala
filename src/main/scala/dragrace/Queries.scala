package dragrace

import io.getquill.*

import QuillContext.*

object Queries:
  // Return all the samples in the database. Note that the quote is a macro that will
  // generate the query at compile time. VSCode will show the resulting SQL
  // in the IDE where you "run" it.
  inline def samples = quote(query[Samples])

  // Refines the samples() query with a filter, demonstrating query re-use
  inline def sample(id: Int) = quote(samples.filter(_.id == lift(id)))

  inline def insertSample(s: Samples) = quote(samples.insertValue(lift(s)))

  inline def deleteSample(id: Int) = quote(samples.filter(_.id == lift(id)).delete)

  // Toss a list of samples into the db
  inline def insertSamples(ss: Seq[Samples]) = quote {
    liftQuery(ss).foreach(s => samples.insertValue(s))
  }

  // Drop all entries in the table
  inline def deleteAllSamples = quote(samples.delete)

end Queries
