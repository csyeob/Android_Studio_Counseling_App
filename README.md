## :family: Android_Studio_Counseling_App
## 수뭉이의 금쪽상담소_모바일프로그래밍_상명대학교
### :point_up: Stack
<img alt="Android" src ="https://img.shields.io/badge/Android-3DDC84.svg?&style=for-the-badge&logo=Android&logoColor=white"/> <img alt="Google" src ="https://img.shields.io/badge/Google Cloud-4285F4.svg?&style=for-the-badge&logo=Google Cloud&logoColor=white"/> <img alt="NLP" src ="https://img.shields.io/badge/NLP(sentiment analysis)-E37400.svg?&style=for-the-badge&logo=Google Analytics&logoColor=black"/> <img alt="database" src ="https://img.shields.io/badge/Firebase-FFCA28.svg?&style=for-the-badge&logo=Firebase&logoColor=black"/>  
### :v: App Design
<img alt="Adobe XD" src ="https://img.shields.io/badge/Adobe XD-FF61F6.svg?&style=for-the-badge&logo=Adobe XD&logoColor=black"/> <img alt="Power Point" src ="https://img.shields.io/badge/PowerPoint-B7472A.svg?&style=for-the-badge&logo=Microsoft PowerPoint&logoColor=black"/>
----
### Google NLP(sentiment analysis)
**구글 자연어처리 API키를 가져와 main - res - raw파일에 credential.json파일 삽입(자신의 API키를 사용)**<br>
Import the Google Natural Language Processing API key and insert the credential.json file into the main-res-raw file (using your own API key)


### File Logic
````    
📂 manifests - 📄 AndroidManifest.xml <br>
📂 java 
├─ 📄 Activity.class - layout Activity file 액티비티 파일들 
├─ 📄 AccessTokenLeader - google API processor 구글 API키 처리
├─ 📄 Adapater - cardView에 사용
├─ 📄 ChildInfo - 자식 등록정보 child_register_info
├─ 📄 UserInfo - 사용자 등록정보 User_register_info
├─ 📄 Question_Answer - 상담 질문 및 결과내용 counseling question and result
└─ 📄 resultValue - 상담 결과값 판단 counseling result judgement


📂 asset - 📄 font.ttf (ttf파일이여야함. must be ttf file.)
📂 res
└─ 📂 drawable / 📂 font / 📂 layout / 📂 values / 📂 raw
    └ img.png     └ font.ttf └ layout_files(.xml)     └ credential.json
````

### 앱 구조 및 기능 App Structure and Features
**splash 및 로그인** - SplashActivity.java, LoginActivity.java <br>
**회원가입** - RegisterActivity.java<br>
**자녀등록** - ParentActivity.java (부모계정)<br>
**상담 기능** - MainActivity.java, NoticeActivity.java (sentiment analysis, tts, stt)<br>
**상담 결과(child)** - AnswerActivtiy.java (Firebase - realtimeDB) - 자식쪽<br>
**상담 결과(parent)** - ParentResult.java <br>
![](https://user-images.githubusercontent.com/50544455/175820081-7edbaba5-b610-4383-ac46-8ced6c597d78.png)<br>
![](https://user-images.githubusercontent.com/50544455/175820107-01e4857d-cec2-4c67-8c77-8228b17ddcda.png)<br>

**감정분석(sentimnet analysis)**<br>
Google NLP(구글 자연어처리) 감정분석 모델을 사용하여 text로 답을 작성하면 score -0.9 ~ 0.9로 반환하여 점수에 따른 감정 변화 측정<br>
<img src="https://user-images.githubusercontent.com/50544455/175820133-18192fe1-10f3-47e6-9409-abfb21092fff.png" width="600" height="500"/><br>
**TTS 및 STT** <br>
STT - 음성으로 질문에 대한 답변을 받아 적어준다. ⏺️<br>
TTS - TextView 상담질문을 음성으로 들려준다. 👂<br>
<img src = "https://user-images.githubusercontent.com/50544455/175820150-5fc7d0f2-a7c8-4771-bc1c-e8d1a5272aa0.png" width="300" height="500"/><br>

**Pdf 저장하기**<br>
상담 결과 용지를 앱을 사용하지 않아도 pdf로 받아 볼 수 있음. 📄<br>
<img src="https://user-images.githubusercontent.com/50544455/175820177-65c0cfb7-ee1b-4396-bcbf-cf9391d8f1ce.png" width="600" height="500"/><br>


