AWS Redshift 21Feb13
=============================

```php
php > $connect = pg_connect('host=test.cmephqy3gkvj.us-east-1.redshift.amazonaws.com port=5439 dbname=test user=root password=...');
php > $rec = pg_query($connect, 'SELECT now()');
php > var_dump(pg_fetch_array($rec));
array(2) {
  [0] =>
  string(29) "2013-02-15 17:54:45.479168+00"
  'now' =>
  string(29) "2013-02-15 17:54:45.479168+00"
}

php > $rec = pg_query($connect, 'CREATE TABLE test.public.table1 (c1 int)');
php > $rec = pg_query($connect, 'INSERT INTO table1 values (10)');
php > echo time() . "\n"; $rec = pg_query($connect, 'INSERT INTO table1 values (20)'); echo time() . "\n";
1360951053
1360951053
php > echo time() . "\n"; $rec = pg_query($connect, 'INSERT INTO table1 values (30)'); echo time() . "\n";
1360951059
1360951059
php > $rec = pg_query($connect, 'SELECT * from table1');
php > var_dump(pg_fetch_array($rec));
array(2) {
  [0] =>
  string(2) "10"
  'c1' =>
  string(2) "10"
}
php > echo time() . "\n"; $rec = pg_query($connect, 'INSERT INTO table1 values (30),(40),(50),(60)'); echo time() . "\n";
1360951165
1360951168
php > while($arr = pg_fetch_row($rec)){ var_dump($arr); }
array(1) {
  [0] =>
  string(2) "30"
}
array(1) {
  [0] =>
  string(2) "50"
}
array(1) {
  [0] =>
  string(2) "20"
}
array(1) {
  [0] =>
  string(2) "30"
}
array(1) {
  [0] =>
  string(2) "60"
}
array(1) {
  [0] =>
  string(2) "40"
}
```
