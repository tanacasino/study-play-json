package com.example.libs.json

import play.api.libs.json.{JsNull, OWrites}

package object syntax {

  /**
    * OWritesとOptionの項目キー名を受け取って、JSONにシリアライズする
    * Optionのキー名がJsObjectに存在しない場合に明示的に JsNullで突っ込む
    * @param ow
    * @tparam A
    */
  final implicit class OWritesOps[A](val ow: OWrites[A]) {
    def omitNull(keysNullable: List[String]): OWrites[A] = OWrites { o =>
      keysNullable.foldLeft(ow.writes(o)) {
        case (json, key) if !json.keys.contains(key) => json + (key -> JsNull)
        case (json, _) => json
      }
    }
  }

}
