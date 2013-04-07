Handler Socket
=============================

Introduction
------------

At August of 2012 I step over Handler Socket:

"HandlerSocket is a MySQL plugin that implements a NoSQL protocol for MySQL. This allows applications to communicate more directly with MySQL storage engines, without the overhead associated with using SQL. This includes operations such as parsing and optimizing queries, as well as table handling operations (opening, locking, unlocking, closing). As a result, using HandlerSocket can provide much better performance for certain applications that using normal SQLapplication protocols."

@ http://www.percona.com/doc/percona-server/5.5/performance/handlersocket.html

Interesting so I google a little and found a [blog post of the author](http://yoshinorimatsunobu.blogspot.pt/2010/10/using-mysql-as-nosql-story-for.html) about it with the following benchmarks:

<table>
  <tr>
    <th></th>
    <th>approx qps</th>
    <th>server CPU util</th>
  </tr>
  <tr>
    <td>MySQL via SQL</td>
    <td>105,000</td>
    <td>%us 60%  %sy 28%</td>    
  </tr>
  <tr>
    <td>Memcached</td>
    <td>420,000</td>
    <td>%us  8%  %sy 88%</td>    
  </tr>
  <tr>
    <td>MySQL via HandlerSocket</td>
    <td>750,000</td>
    <td>%us 45%  %sy 53%</td>    
  </tr>  
</table>

The value of qps (querys per second) is so high because data fit in innodb buffer pool, so is CPU bounded instead of IO bounded.

So i runned some benchmarks, because I wanted to test in a more common environment: not all data fit on memory just indexes fit on memory.

Environment
------------

I installed Percona Server and enabled HandlerSocket with [default configuratin](http://www.percona.com/doc/percona-server/5.5/performance/handlersocket.html). [Large server 7.5GB at Amazon EC2](http://aws.amazon.com/ec2/instance-types/).

I had a table with 15 columns and 5 million rows, indexes fit on innodb buffer pool the full data data no. I expected a smaller difference that 7x more. Because would be IO bound, and Amazon EC2 don't excel on IO.

So i runned 2 PHP's one using handlersocket and another PHP mysql common functions (not mysqli). PHP generate a random number between 1 and 5M and then get email (just one column of the 15) by ID (primary key). This was done in a while of 100K iterations.

To communicate with HandlerSocket at PHP I installed and used [PHP HandlerSocket](http://code.google.com/p/php-handlersocket/).

Results
------------

One client: Using Handler 65% more qps than using MySQL via SQL.

Ten clients: Using handler socket three times more qps than using MySQL via SQL

Below you can see iostat output with ten regular MySQL clients were running:

```bash
avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          69,14    0,00   30,16    0,15    0,00    0,55

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             57,00      1884,80        46,40      18848        464

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          69,04    0,00   30,16    0,25    0,05    0,50

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             56,20      1848,80        44,80      18488        448

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          69,27    0,00   29,93    0,15    0,05    0,60

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             48,40      1563,20        50,40      15632        504
```

And below you can see iostat output with 10 Handler Socket clients: 

```bash
avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          52,33    0,00   47,67    0,00    0,00    0,00

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             13,70       345,60        48,00       3456        480

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          52,65    0,00   47,35    0,00    0,00    0,00

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             11,80       257,60        50,40       2576        504

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          53,22    0,00   46,73    0,00    0,05    0,00

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf             13,00       297,60        50,40       2976        504

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          52,51    0,00   47,04    0,35    0,05    0,05

Device:            tps   Blk_read/s   Blk_wrtn/s   Blk_read   Blk_wrtn
xvdap1            0,00         0,00         0,00          0          0
xvdf              9,50       185,60        51,20       1856        512

avg-cpu:  %user   %nice %system %iowait  %steal   %idle
          50,38    0,00   46,68    2,18    0,10    0,66
```

You can notice a save of 15% on CPU.

Conclusions
------------

Handler Socket had performance gains of 300% and reduce the CPU being used, the improve of performance is great, less than the blog post of the author as expected but really impressive.

The performance gains of using Handler Socket can be explained due to bypass of MySQL overhead like SQL parsing and optimizing, locks, query cache and so on. Handler Socket communicates directly with InnoDB engine.

Not everything is perfect, the syntax looks akward, don't have a real authentication, just one pretty bad with plain passwords. But HandlerSocket listen in 2 ports (one for write other for reads) so you can security it using iptables. Writes in HandlerSocket don't invalidate MySQL Query Cache, this can be good or bad, good if you are ok with some stale data.

Update
------------

MySQL 5.6 supports [Memcache protocol to access InnoDB tables](http://dev.mysql.com/tech-resources/articles/whats-new-in-mysql-5.6.html#nosql), probably inspired in Handler Socket, but using Memcache protocol is much better idea, making the adoption of this new feature much easier to developers that Handler Socket.

I found a benchmarkabout memcache protocol to access InnoDB tables at [MySQL performance blog](http://www.mysqlperformanceblog.com/2013/03/29/mysql-5-6-innodb-memcached-plugin-as-a-caching-layer/). I was quite disappointed with the results, anyway a benchmark with multiple clients and information about system load would be more interesting and more close to reality.





