package com.example.views

import com.example.models._
import org.scalatest.FunSuite
import play.api.libs.json.Json

class PersonReadsWritesSpec extends FunSuite {

  import PersonWrites._
  import PersonReads._

  val person = Person(Id(1L), PersonName("tarou"), PersonAge(30), Gender.Male, note = None)

  test("Writes JSON with null value") {
    val personJson = Json.toJson(person)
    assert(personJson.toString() === """{"personId":"1","name":"tarou","age":30,"gender":"M","note":null}""")
  }

  test("Reads JSON") {
    val json = Json.parse("""{"personId":"1","name":"tarou","age":30,"gender":"M","note":null}""")
    val result = Json.fromJson[Person](json)
    assert(result.isSuccess === true)
    result.foreach { decodedPerson =>
      assert(decodedPerson === person)
    }

    val invalidJson = Json.parse("""{"personId":"notanumber","name":"","age":-1,"gender":"Z"}""")
    val invalidResult = Json.fromJson[Person](invalidJson)
    assert(invalidResult.isError === true)
    val errors = invalidResult.asEither.left.get
    assert(errors.size === 4)
  }

}
