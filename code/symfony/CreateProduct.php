<?php

use Acme\StoreBundle\Entity\Product;
use Symfony\Component\HttpFoundation\Response;

// ...
public function createAction()
{
    $product = new Product();
    $product->setName('A Foo Bar');
    $product->setPrice('19.99');
    $product->setDescription('Lorem ipsum dolor');

    $em = $this->getDoctrine()->getManager();
    $em->persist($product);
    $em->flush();
    
    return new Response('Created product id '.$product->getId());
}
