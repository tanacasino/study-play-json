package com.example.libs.json

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

object ExtractKeys {

  def optionalClassParamsAsList[T: c.WeakTypeTag](c: whitebox.Context): c.Expr[List[String]] = {
    import c.universe._


    val companioned = weakTypeOf[T].typeSymbol
    val companionSymbol = companioned.companion
    val companionType = companionSymbol.typeSignature


    companionType.decl(TermName("apply")) match {
      case NoSymbol => c.abort(c.enclosingPosition, "No apply function found")
      case s =>
        val apply = s.asMethod
        val params = apply.paramLists.head
        val paramsNullable = params filter (_.typeSignature.typeConstructor <:< typeOf[Option[_]].typeConstructor) map (_.name.toString)


        val listApply = if (paramsNullable.nonEmpty)
          s""" "${paramsNullable.mkString("""","""")}" """
        else
          ""


        val tree = c.parse(s"List($listApply)")


        c.typecheck(tree)
        c.Expr[List[String]](tree)
    }
  }

  def extractKeysOpt[T]: List[String] = macro ExtractKeys.optionalClassParamsAsList[T]

}
