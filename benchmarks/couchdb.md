Apache CouchDB
=============================

Introduction
------------

At August of 2012 I had a look on CouchDB project, starting by documentation (that is quite good). I was looking for some NoSQL document oriented, so I run some benchmarks. The benchmarks were oriented to the needs at the time, write-intensive workload.

Environment
------------

**Server type**: AWS Cloud Europe Ireland - EC2 Large (m1.large)

**CPU**: 4 EC2 Compute Units (2 virtual cores with 2 EC2 Compute Units each) [FAQ](http://aws.amazon.com/ec2/faqs/#What_is_an_EC2_Compute_Unit_and_why_did_you_introduce_it)

**Memory**: 7.5GB

**I/O Performance**: Moderate

**OS**: Amazon Linux AMI

**Disks**: 1 EBS

**Dedicated/fresh to test**: Yes

More info
------------

I used PHP to run the tests and too ways of accessing CouchDB:
- [PHP CouchDB extension](https://github.com/akissa/php-couchdb)
- Curl

The data being inserted is data about users. All the time to fetch data from MySQL was subtracted from benchmarks. The tests were run several times until consistent timing was achieved. 

Single node
------------

### Single client

#### Put single documents

In this benchmark i just ran a single PHP client sending data to CouchDB using put, each put one document.

<table>
  <tr>
    <th>Documents</th>
    <th>Client</th>
    <th>Docs/s</th>
  </tr>
  <tr>
    <td>1M</td>
    <td>PHP CouchDB extension</td>
    <td>207</td>
  </tr>
  <tr>
    <td>1M</td>
    <td>Curl</td>
    <td>107</td>
  </tr>  
</table>

CouchDB reported that 1M users occupies 7.8GB, after "ask" CouchDB to compact it passed do 1.6GB. Almost double it occupied on MySQL.

#### Put bulks

<table>
  <tr>
    <th>Documents</th>
    <th>Client</th>
    <th>Bulk size</th>
    <th>Docs/s</th>
  </tr>
  <tr>
    <td>50K</td>
    <td>Curl</td>
    <td>50</td>
    <td>804</td>
  </tr>
  <tr>
    <td>50K</td>
    <td>Curl</td>
    <td>100</td>
    <td>1183</td>
  </tr>  
  <tr>
    <td>50K</td>
    <td>Curl</td>
    <td>200</td>
    <td>1846</td>
  </tr>    
</table>

Master slave
------------

The master and slave are placed in the same region (Europe Ireland) but in [different zones](http://aws.amazon.com/ec2/faqs/#How_isolated_are_Availability_Zones_from_one_another). The replication used was pull replication.

<table>
  <tr>
    <th>Documents</th>
    <th>Client</th>
    <th>Bulk size</th>
    <th>Docs/s</th>
  </tr>
  <tr>
    <td>50K</td>
    <td>Curl</td>
    <td>50</td>
    <td>618</td>
  </tr>
</table>

This results means a drop from 804 docs/s to 618 docs/s, it means a drop of around 1/4 on throughput against  single node.

Master master
------------

The two masters are placed in the same region (Europe Ireland) but in [different zones](http://aws.amazon.com/ec2/faqs/#How_isolated_are_Availability_Zones_from_one_another).

<table>
  <tr>
    <th>Documents</th>
    <th>Client</th>
    <th>Bulk size</th>
    <th>Docs/s</th>
  </tr>
  <tr>
    <td>50K</td>
    <td>Curl</td>
    <td>50</td>
    <td>408</td>
  </tr>
</table>

This results means a drop from 804 docs/s to 408 docs/s, it means a drop of around 1/2 on throughput against single node.

Retrieve documents
------------

To retrieve documents i used the [GET](http://wiki.apache.org/couchdb/HTTP_Document_API#GET)

With one client of PHP using curl the best i got was ~70 documents retrieved per second (with 5M users 36GB). Then i compacted and it improved to ~90 documents/s.

Running 10 clients I got around 40 documents/s each, so a total of 400 documents/s.

Then I added 4 volumes to the server and raid 0, with the same clients I got a total of 910 documents/s.

Compacting
------------

"Compaction compresses the database file by removing unused sections created during updates. Old revisions of documents are also removed from the database though a small amount of meta data is kept for use in conflict during replication. The number of revisions (default of 1000) can be configured using the _revs_limit URL endpoint, available since version 0.8-incubating."

@ http://wiki.apache.org/couchdb/Compaction

I had a table with 5M users 36GB, compacting it took around 20 minutes, during that time the throughput was reduced in half.

What i liked in CouchDB
------------

- Very easy to set up replication;
- easy to start with;
- nice web interface Futon;
- the design, just write end of file guarantees data keeps safe, and write throughput is stable. 

What I didn't like in CouchDB
------------

- No automatic compaction;
- Huge space on Disk (even after compacting), space is cheap but then requires more IO to fetch data, more information on a [ticket](https://issues.apache.org/jira/browse/COUCHDB-1092) on JIRA;
- No partial updates (I know the way it works it needs to fetch the document and update as a whole, but a command could be provided to handle that);
- Not managing and caching data, leaving all the work to OS, doesn't work good;
- Performance is far far from great.

I found an old post about [10 Annoying Things About CouchDB](http://www.paperplanes.de/2010/7/26/10_annoying_things_about_couchdb.html).
