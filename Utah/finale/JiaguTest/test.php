<?php
$conn = oci_connect('C##river','123','192.168.43.58/temp');
if (!$conn)
{
    $Error = oci_error();
    print htmlentities($Error['message']);
    exit;
}
else
{
    echo "Connected Oracle Successd!"."<br>";
    ocilogoff($conn);
}
?>