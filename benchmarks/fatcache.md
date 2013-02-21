Fatcache
=============================

Introduction
------------

I found Fatcache in February of 2012 (https://github.com/twitter/fatcache):

> fatcache is memcache on SSD. Think of fatcache as a cache for your big data.

I didn't look at fatcache as an extensin of memcache in performance, like something on the middle between memcache and common RDBMS in performance, you can look at it like that.

I see a lot of potential on Fatcache, like sometimes you want to cache some data for a period of time, after some hours or days will be not relevant anymore. You can save it on a regular RDBMS with a column date for expire date, but then there is the need of have some crontab or daemon removing expire keys. Sometimes this data is too big to be hold on Memcache, you don't want to pay for so much memory when RDBMS performance on disk is enough. But you want the concept of key-value and expire date.

I did some benchmarks just to be sure that it have enough performance, doesn't have to excel on it.

Build
------------

I had problems build from tarball. So i cloned the repository and follow the instructions on [README](https://github.com/twitter/fatcache/blob/master/README.md). I need to install some packages like: autotools.

Environment
------------

**Server type**: AWS Cloud US EAST Virginia - Small

**CPU**: 1 Compute Unit [FAQ](http://aws.amazon.com/ec2/faqs/#What_is_an_EC2_Compute_Unit_and_why_did_you_introduce_it)

**Memory**: 1.7GB

**I/O Performance**: Moderate

**OS**: Amazon Linux AMI release 2012.09

**Proc/version**: Linux version 3.2.30-49.59.amzn1.i686 (mockbuild@gobi-build-31003) (gcc version 4.4.6 20110731 (Red Hat 4.4.6-3) (GCC) ) #1 SMP Wed Oct 3 19:55:00 UTC 2012

**Disks**: 4 EBS 50GB

**RAID**: 0 (mdadm -C /dev/md0 --chunk=256 -n 4 -l 0 ...)

**Dedicated/fresh to test**: Yes

Version
------------

# src/fatcache --version
This is fatcache-0.1.0

More environment
------------

**Cache read timings** (hdparm -T)

```
/dev/md0:
 Timing cached reads:   4842 MB in  2.01 seconds = 2409.97 MB/sec

/dev/md0:
 Timing cached reads:   4336 MB in  2.00 seconds = 2169.82 MB/sec

/dev/md0:
 Timing cached reads:   4748 MB in  2.00 seconds = 2372.18 MB/sec
 ```
 
 **Device read timings** (hdparm -t)
 
 ```
/dev/md0:
 Timing buffered disk reads:  322 MB in  3.01 seconds = 106.88 MB/sec

/dev/md0:
 Timing buffered disk reads:  324 MB in  3.02 seconds = 107.31 MB/sec

/dev/md0:
 Timing buffered disk reads:  314 MB in  3.01 seconds = 104.39 MB/sec
 ```
 
**CPU**

 ```bash
$ time echo "scale=5000; a(1)*4" | bc -l > /dev/null
real  1m31.503s
user	1m31.130s
sys	0m0.024s

$ time echo "scale=5000; a(1)*4" | bc -l > /dev/null
real  1m31.266s
user	1m31.198s
sys	0m0.000s

$ time echo "scale=5000; a(1)*4" | bc -l > /dev/null
real  1m31.369s
user	1m31.210s
sys	0m0.012s
```
 
 [Simple benchmark](http://duguo.org/kb/server/test/simple_linux_benchmark.html)
 
Code
------------

**Code 1 - Just write**

With this code md5 time is being taking in consideration on total time, the purpose of this benchmark was not to prove that Fatcache is faster than X, just that Fatcache behaves well in a poor server and with bad IO.

```php
<?php
$m = new Memcached();
$m->addServer('localhost', 11211, 33);
 
$items = 100000;
 
$start = time();
for ($i = 1; $i <= $items; ++$i) {
    $key = md5(rand());
    $value = md5(rand()). md5(rand()) . md5(rand()) . md5(rand()) . md5(rand());
    $m->set($key, $value, rand(60, 600));
//    $m->get($key);
}
$end = time();
 
echo "\nSummary\n";
echo "Items: $items\n";
echo "Start time: $start\n";
echo "End time: $end\n";
echo "Operations/s: " . ($items/($end-$start)) . "\n";
```

**Code 2 - Write and read**

Equal to Code 1 but with the line of get uncommented.

**Code 3**

Results
------------

**Code 1**

<table>
  <tr>
    <th>Number of items</th>
    <th>Start time</th>
    <th>Final time</th>
    <th>Time/s</th>
    <th>Operations/s</th>
  </tr>
  <tr>
    <td>100K</td>
    <td>1361323435</td>
    <td>1361323448</td>
    <td>13</td>
    <td>~7.7K</td>
  </tr>
  <tr>
    <td>1M</td>
    <td>1361323670</td>
    <td>1361323795</td>
    <td>125</td>
    <td>8K</td>
  </tr>  
</table>

**Code 2**

<table>
  <tr>
    <th>Number of items</th>
    <th>Start time</th>
    <th>Final time</th>
    <th>Time/s</th>
    <th>Operations/s</th>
  </tr>
  <tr>
    <td>100K</td>
    <td>1361324006</td>
    <td>1361324030</td>
    <td>13</td>
    <td>~8.3K</td>
  </tr>
</table>

Note: ~8.3K means 4.15K sets and 4.15 gets
