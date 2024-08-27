# test-task
Реализовал автотесты для тестового задания с помощью данных зависимостей
dependencies {
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testImplementation group: 'io.rest-assured', name: 'rest-assured', version: '5.4.0'
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.10.1'
}
