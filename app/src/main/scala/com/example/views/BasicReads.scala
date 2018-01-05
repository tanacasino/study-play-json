package com.example.views

import scala.util.Try

import com.example.libs.Prism
import com.example.models.{ Enum, Id }
import play.api.libs.json.{JsError, JsString, JsSuccess, Reads}

object BasicReads {

  final implicit def idReads[A]: Reads[Id[A]] = Reads {
    case JsString(value) =>
      Try(value.toLong).map(Id.apply[A]).toOption match {
        case Some(id) => JsSuccess(id)
        case _ => JsError("error.type")
      }
    case _ => JsError("error.type")
  }

  final implicit def enumReads[A, B <: Enum[A]](implicit prism: Prism[A, B], reads: Reads[A]): Reads[B] = Reads { json =>
    reads.reads(json).flatMap { a =>
      prism.toOpt(a) match {
        case Some(b) => JsSuccess(b)
        case None => JsError("error.lookup")
      }
    }
  }

}
