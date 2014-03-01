<?php

namespace ESO\RESTDemoBundle\Controller;

use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\HttpFoundation\Response;

class DefaultController
{
    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     */
    public function indexAction($name)
    {
        return array('name' => $name);
    }
}
