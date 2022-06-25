## :family: Android_Studio_Counseling_App
## 수뭉이의 금쪽상담소_모바일프로그래밍_상명대학교
#### :point_up: Stack
<img alt="Android" src ="https://img.shields.io/badge/Android-3DDC84.svg?&style=for-the-badge&logo=Android&logoColor=white"/> <img alt="Google" src ="https://img.shields.io/badge/Google Cloud-4285F4.svg?&style=for-the-badge&logo=Google Cloud&logoColor=white"/> <img alt="NLP" src ="https://img.shields.io/badge/NLP(sentiment analysis)-E37400.svg?&style=for-the-badge&logo=Google Analytics&logoColor=black"/> <img alt="database" src ="https://img.shields.io/badge/Firebase-FFCA28.svg?&style=for-the-badge&logo=Firebase&logoColor=black"/>  
#### :v: App Design
<img alt="Adobe XD" src ="https://img.shields.io/badge/Adobe XD-FF61F6.svg?&style=for-the-badge&logo=Adobe XD&logoColor=black"/> <img alt="Power Point" src ="https://img.shields.io/badge/PowerPoint-B7472A.svg?&style=for-the-badge&logo=Microsoft PowerPoint&logoColor=black"/>
----
#### Google NLP(sentiment analysis)
1. **구글 자연어처리 API키를 가져와 main - res - raw파일에 credential.json파일 삽입(자신의 API키를 사용)**<br>
Import the Google Natural Language Processing API key and insert the credential.json file into the main-res-raw file (using your own API key)

#### File Logic
:open_file_folder: **manifests** - :page_facing_up: AndroidManifest.xml <br>
:open_file_folder: **java** <br>
└─ :page_facing_up: **Activity.class** - layout Activity file 액티비티 파일들 <br>
└─ :page_facing_up: **AccessTokenLeader** - google API processor 구글 API키 처리<br>
└─ :page_facing_up: **Adapater** - cardView에 사용<br>
└─ :page_facing_up: **ChildInfo** - 자식 등록정보 child_register_info<br>
└─ :page_facing_up: **UserInfo** - 사용자 등록정보 User_register_info<br>
└─ :page_facing_up: **Question_Answer** - 상담 질문 및 결과내용 counseling question and result<br>
└─ :page_facing_up: **resultValue** - 상담 결과값 판단 counseling result judgement<br>


:open_file_folder: **asset** - :page_facing_up: font.ttf (ttf파일이여야함. must be ttf file.)<br>
:open_file_folder: **res** <br>
└─ :open_file_folder: **drawable** / :open_file_folder: **font** / :open_file_folder: **layout** / :open_file_folder: **values** / 📂 **raw** <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└ img.png    &nbsp;&nbsp;└ font.ttf &nbsp;&nbsp;└ layout_files(.xml)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└ credential.json




