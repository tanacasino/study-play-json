package com.example.libs.json

import scala.reflect.macros.whitebox
import scala.language.experimental.macros

/**
  *
  * こちらを参考にplay-jsonのマクロで作成した Writes に Optionのフィールドに明示的にnullを突っ込む対応を
  * するためのマクロを使った実装を真似たもの
  *
  * https://groups.google.com/forum/#!msg/play-framework/b5xF1GdwQGI/PjO7bVDxoHwJ
  */
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

  /**
    * 指定された [[T]] クラスのOptionのフィールドのキー名一覧を抽出する
    *
    * @tparam T
    * @return キー名のリスト
    */
  def extractKeysOpt[T]: List[String] = macro ExtractKeys.optionalClassParamsAsList[T]

}
