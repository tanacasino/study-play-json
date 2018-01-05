package com.example.views

import com.example.libs.Prism
import com.example.models.{Enum, Id, ValueClass}
import play.api.libs.json.{JsString, Writes}

object BasicWrites {

  final implicit def idWrites[A]: Writes[Id[A]] = Writes { id =>
    JsString(id.value.toString)
  }

  final implicit def valueClassWrites[A: Writes]: Writes[ValueClass[A]] = Writes { valueClass =>
    implicitly[Writes[A]].writes(valueClass.value)
  }

  final implicit def enumWrites[A, B <: Enum[A]](implicit prism: Prism[A, B], writes: Writes[A]): Writes[B] = Writes { b =>
    writes.writes(prism.from(b))
  }

}
