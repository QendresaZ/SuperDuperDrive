package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(Credential credential) {
        getEncodedKey(credential);
        return credentialMapper.saveCredential(credential);
    }

    private void getEncodedKey(Credential credential) {
        String encodedKey = getRandomEncodedKey();
        String encryptedPass = encryptCredentialPass(encodedKey, credential.getPassword());
        credential.setPassword(encryptedPass);
        credential.setKey(encodedKey);
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public List<Credential> getCredentialsbyUserId(Integer userId) {
        return  credentialMapper.getCredentialByUserId(userId);
    }

    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public String encryptCredentialPass(String encodedKey, String password) {
        return encryptionService.encryptValue(password, encodedKey);
    }

    public String decryptCredentialPass(String encryptedPass, String encodedKey) {
        return encryptionService.decryptValue(encryptedPass, encodedKey);
    }

    private String getRandomEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public int updateCredential(Credential credential) {
        getEncodedKey(credential);
         return credentialMapper.updateCredential(credential);
    }
}
