package com.example.views

import com.example.libs.json.ExtractKeys
import com.example.libs.json.syntax._
import com.example.models.Person
import play.api.libs.json.{Json, OWrites}

object PersonWrites {

  import BasicWrites._

  final implicit val personWrites: OWrites[Person] = Json.writes[Person].omitNull(ExtractKeys.extractKeysOpt[Person])

}
