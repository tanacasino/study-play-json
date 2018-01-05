package com.example.libs.json

import org.scalatest.FunSuite

class ExtractKeysSpec extends FunSuite {

  test("case classのOptionのフィールドのキー名を取得できる") {
    val keys = ExtractKeys.extractKeysOpt[ExtractKeysSpec.Foo]
    assert(keys.size === 2)
    assert(keys === List("c", "e"))
  }

}


object ExtractKeysSpec {

  case class Foo(a: Int, b: Int, c: Option[Int], d: String, e: Option[String], f: Boolean)

}
