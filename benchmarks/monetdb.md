MonetDB
=============================

Introduction
------------

I was looking for a column oriented DB and I found [MonetDB the column-store pioneer](http://www.monetdb.org/Home) at the time I was evaluating several tecnhologies, some just reading anothers like InfiniDB benchmarking. So I compare InfiniDB against MonetDB, but I will just post MonetDB results (that overpass InfiniDB hugely).

MonetDB is not well known, I never heard about it, it is not a real commercial product but more an academic project, but a great project.

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
</table>

Data
------------

Table with 33 columns, with 20 columns as integers, 8 as varchar, 4 float and 1 datetime.

With data on MySQL running show table status, the table have have a average row length of 203 bytes.
