package dragrace

import io.getquill.{PostgresZioJdbcContext, Literal}

// Reify a context we can use to access the database. This can be any
// database type supported by Quill but it must be known at compile time
// for the data service layer to compile. You cannot layer a context
object QuillContext extends PostgresZioJdbcContext(Literal)
