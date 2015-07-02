spring-boot tips

- thymeleafは基本的にth:~~とする
- thymeleafはhtmlタグのxml namespaceにlayoutのやつを加えると画面の共通部分を切り出せる
- logbackはspringフレームワークが持つものを流用できる
- flywayのSQLマイグレーションファイルのアンダースコアは２つ
- flywayでSQLのタイプミスとかで checksum mismatchになったらflyway:clean か flyway:repairで直す
- herokuコマンド
  heroku --version
  heroku login
  git init
  git add -A
  git commit -m "FIrst Commit" -a
  git push heroku master
  