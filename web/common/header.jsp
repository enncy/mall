<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh-CN"><head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Mall 电商系统</title>

    <!-- Bootstrap core CSS -->

    <link href="https://v5.bootcss.com/docs/5.1/dist/css/bootstrap.min.css" rel="stylesheet"  >

    <script src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"
    ></script>
    <style>
        .container {
            max-width: 1080px !important;
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
            color: #8e8e8e;
            transition: color .15s ease-in-out;
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
            flex: 1;
        }
        @media (min-width: 768px) {
            .flex-md-equal > * {
                flex: 1;
            }
        }


        .alert{
            line-height: 1 !important;
        }


        .overflow-hidden { overflow: hidden; }

        .container {
            padding: 60px 15px 0 !important;
        }

    </style>


</head>
<body class="d-flex flex-column h-100">