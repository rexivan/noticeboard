package com.example.noticeboard;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class AzureBlob {

    public static String SecretaccountKey = "hfsgUsJdgDUYrIXc8OefW2iYDUzrxKY7Pps4OSg8DogrXv5DYLh0NQaXd9xYeHVbGoJcncqh7bC7ZDUC2Z2lag==";
    // String localImageFile = "C:\\temp\\UGC2\\Products\\Ladies\\0918947003.jpg";
    // String destFileName = "image2.jpg";

    public static boolean uploadFileToBlobStorage(String accountKey, String localImageFile,  String destFileName)    {
        String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=advertimages;AccountKey=" + accountKey + ";EndpointSuffix=core.windows.net";
        System.out.println("in BLOB upload, file." + localImageFile);
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container=null;

        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("images");

            // Create the container if it does not exist with public access.
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
            CloudBlockBlob blob = container.getBlockBlobReference(destFileName);
            blob.uploadFromFile(localImageFile); //Creating blob and uploading file to it
            return true;
        }

        catch (StorageException ex)
        {
            System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s", ex.getHttpStatusCode(), ex.getErrorCode()));
        }
        catch (Exception ex)
        {
            System.out.println("Blob Exception:" + ex.getMessage());
        }
        finally
        {
            System.out.println("BLOB-Upload - finally.");
            return false;
        }
    }
}
