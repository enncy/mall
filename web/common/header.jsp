<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN"><head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mall 电商系统</title>

    <!-- Bootstrap core CSS -->
    <link href="https://v4.bootcss.com/docs/4.6/dist/css/bootstrap.min.css" rel="stylesheet"  >


    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .container {
            max-width: 960px;
        }

        /*
         * Custom translucent site header
         */

        .site-header {
            background-color: rgba(0, 0, 0, .85);
            -webkit-backdrop-filter: saturate(180%) blur(20px);
            backdrop-filter: saturate(180%) blur(20px);
        }
        .site-header a {
            color: #999;
            transition: ease-in-out color .15s;
        }
        .site-header a:hover {
            color: #fff;
            text-decoration: none;
        }

        /*
         * Dummy devices (replace them with your own or something else entirely!)
         */

        .product-device {
            position: absolute;
            right: 10%;
            bottom: -30%;
            width: 300px;
            height: 540px;
            background-color: #333;
            border-radius: 21px;
            -webkit-transform: rotate(30deg);
            transform: rotate(30deg);
        }

        .product-device::before {
            position: absolute;
            top: 10%;
            right: 10px;
            bottom: 10%;
            left: 10px;
            content: "";
            background-color: rgba(255, 255, 255, .1);
            border-radius: 5px;
        }

        .product-device-2 {
            top: -25%;
            right: auto;
            bottom: 0;
            left: 5%;
            background-color: #e5e5e5;
        }


        /*
         * Extra utilities
         */

        .flex-equal > * {
            -ms-flex: 1;
            flex: 1;
        }
        @media (min-width: 768px) {
            .flex-md-equal > * {
                -ms-flex: 1;
                flex: 1;
            }
        }

        .alert{
            line-height: 1 !important;
        }


        .overflow-hidden { overflow: hidden; }


    </style>


</head>
<body>