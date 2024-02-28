package com.sirdave.locationnavigator.exception

class EntityExistsException(message: String) : Exception(message)

class EntityNotFoundException(message: String) : Exception(message)

class PasswordsDoNotMatchException(message: String) : Exception(message)