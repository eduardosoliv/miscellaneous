MonetDB
=============================

Introduction
------------

On October/November of 2012 I was looking for a column oriented DB and I found [MonetDB the column-store pioneer](http://www.monetdb.org/Home) at the time I was evaluating several tecnhologies, some just reading anothers like InfiniDB benchmarking. So I compare InfiniDB against MonetDB, but I will just post MonetDB results (that overpass InfiniDB hugely).

MonetDB is not well known, I never heard about it, it is not a real commercial product but more an academic project, a great project.

Environment
------------

I used AWS Cloud, you can have a look on more detail about instance types in [here](http://aws.amazon.com/ec2/instance-types/).

<table>
  <tr>
    <th>Code</th>
    <th>Name</th>
    <th>API name</th>
    <th>CPU</th>
    <th>Memory GB</th>
    <th>I/O Performance</th>
  </tr>
  <tr>
    <td>S1</td>
    <td>M1 Extra Large Instance</td>
    <td>m1.xlarge</td>
    <td>8 EC2 Compute Units (4 virtual cores with 2 EC2 Compute Units each)</td>
    <td>15</td>
    <td>High</td>
  </tr>
  <tr>
    <td>S2</td>
    <td>High-Memory Double Extra Large Instance</td>
    <td>m2.2xlarge</td>
    <td>13 EC2 Compute Units (4 virtual cores with 3.25 EC2 Compute Units each)</td>
    <td>34.2</td>
    <td>High</td>    
  </tr>
  <tr>
    <td>S3</td>
    <td>High I/O Quadruple Extra Large Instance</td>
    <td>hi1.4xlarge</td>
    <td>35 EC2 Compute Units (16 virtual cores)</td>
    <td>60.5</td>
    <td>Very High</td>
  </tr>  
</table>

**S1 and S2 have 4 volumes EBS raid 0 and S3 have the 2 SSD raid 0.**

Data
------------

Table with 33 columns, with 20 columns as integers, 8 as varchar, 4 float and 1 datetime.

With data on MySQL running show table status, the table have have a average row length of 203 bytes.

Number of rows: 640M so around 121GB on MySQL.

On MonetDB it occupies **77GB**.

Queries
------------

Q1) select count(*) from summary where gender = 'M'; /* Gender have 3 options M F N (unknow) */

Q2) select count(*) from summary where age = 'C' and sent > 100; /* age have low cardinality 5-6 segments */

Q3) select provider, count(*) as cnt from summary group by provider order by cnt desc; /* provider have low cardinality, and is a small string, like hotmail, gmail, etc. */

Q4) select parent_id, gender, sum(clicks) as tc from summary group by parent_id, gender order by tc desc limit 10;

Results
------------

I register a cold time, run the query first time, after import data and restart MonetDB. And I register the time after warm up, usually 4-5 queries was enough to get a stable time. I restarted server and run again to verify the values.

**Instance S1**

<table>
  <tr>
    <th>Query</th>
    <th>Cold time (s)</th>
    <th>After warm up (s)</th>
  </tr>
  <tr>
    <td>Q1</td>
    <td>84</td>
    <td>32.1</td>
  </tr>
  <tr>
    <td>Q2</td>
    <td>121</td>
    <td>33.9</td>
  </tr>
  <tr>
    <td>Q3</td>
    <td>15.4</td>
    <td>10.0</td>
  </tr>
  <tr>
    <td>Q4</td>
    <td>77</td>
    <td>61</td>
  </tr> 
</table>

**Instance S2**

I was short on time and just run Q1 and Q2 on this instance. The times after warm up are better, the instance have double of memory so is expected result.

<table>
  <tr>
    <th>Query</th>
    <th>Cold time (s)</th>
    <th>After warm up (s)</th>
  </tr>
  <tr>
    <td>Q1</td>
    <td>95</td>
    <td>20.7</td>
  </tr>
  <tr>
    <td>Q2</td>
    <td>49.5</td>
    <td>26.4</td>
  </tr>
</table>

**Instance S3 (SSD)**

Improves are huge, and cold time is much closer to warm up time then before, because the 2 SSD with raid 0 have greate performance.

<table>
  <tr>
    <th>Query</th>
    <th>Cold time (s)</th>
    <th>After warm up (s)</th>
  </tr>
  <tr>
    <td>Q1</td>
    <td>21.3</td>
    <td>15.5</td>
  </tr>
  <tr>
    <td>Q2</td>
    <td>30.5</td>
    <td>21.2</td>
  </tr>
  <tr>
    <td>Q3</td>
    <td>4.2</td>
    <td>4.9</td>
  </tr>
  <tr>
    <td>Q4</td>
    <td>11.6</td>
    <td>10.1</td>
  </tr> 
</table>
