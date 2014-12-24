■jsonのスケルトンを生成し、それを手直ししてテーブルスキーマを作成
　テーブル生成時にjsonファイルを渡してテーブルを作る。
$ aws dynamodb create-table --generate-cli-skeleton > tmp/skeleton.json
$ cp tmp/skeleton.json tmp/productCatalog.json

手直しする

$ aws dynamodb create-table --cli-input-json file://tmp/productCatalog.json 

