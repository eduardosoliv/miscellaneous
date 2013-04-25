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
    <th>Name</th>
    <th>API name</th>
    <th>CPU</th>
    <th>Memory GB</th>
    <th>I/O Performance</th>
  </tr>
  <tr>
    <td>M1 Extra Large Instance</td>
    <td>m1.xlarge</td>
    <td>8 EC2 Compute Units (4 virtual cores with 2 EC2 Compute Units each)</td>
    <td>15</td>
    <td>High</td>
  </tr>
  <tr>
    <td>High-Memory Double Extra Large Instance</td>
    <td>m2.2xlarge</td>
    <td>13 EC2 Compute Units (4 virtual cores with 3.25 EC2 Compute Units each)</td>
    <td>34.2</td>
    <td>High</td>    
  </tr>
  <tr>
    <td>High I/O Quadruple Extra Large Instance</td>
    <td>hi1.4xlarge</td>
    <td>35 EC2 Compute Units (16 virtual cores)</td>
    <td>60.5</td>
    <td>Very High</td>
  </tr>  
</table>

Data
------------

Table with 33 columns, with 20 columns as integers, 8 as varchar, 4 float and 1 datetime.

With data on MySQL running show table status, the table have have a average row length of 203 bytes.

Queries
------------

Q1) select count(*) from summary where gender = 'M'; /* Gender have 3 options M F N (unknow) */

Q2) select count(*) from summary where age = 'C' and sent > 100; /* age have low cardinality 5-6 segments */

Q3) select count(*) from summary where gender = 'F' and provider = 'hotmail'; /* provider have low cardinality */

Q4) select provider, count(*) as cnt from summary group by provider order by cnt desc;

Q5) select parent_id, gender, sum(clicks) as tc from summary group by parent_id, gender order by tc desc limit 10;
