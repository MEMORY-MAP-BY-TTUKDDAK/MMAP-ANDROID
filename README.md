# 🌏 Why Memory Map?

- 갤러리를 뒤져가며 추억에 젖었던 경험, 다들 한 번씩은 갖고 계시죠?
- 여러분의 SNS 및 블로그에 방문했던 국가들의 여행기록을 남기신 적, 한 번쯤 있으시죠?
- 내가 방문했던 국가들, 만들었던 추억들... __한 눈에 볼 수 있는 앱이 있다면 어떨까요?__

# 🌸 About Memory Map!
- 여행 기록들을 __시각적으로 표현하는 서비스의 부재__
- 즐거웠던 추억들을 되돌아보며 __어려운 시기 극복__
- 기존의 SNS와는 달리, __지역 중심 시각화를 통해 한눈에 들어오는 뷰 제공__

# 😎 TEAM TTUK-DDAK?

- 숙명여자대학교 소프트웨어학부 3인으로 구성된 __2021 상반기 졸업작품 드림팀!__
- 개발기간 : 1/24 ~ 3/30

  - FrontEnd
    - 최다연  
    <img src = "https://user-images.githubusercontent.com/50194490/113519060-ebf89e80-95c4-11eb-87cd-0967ca586f96.png" width = "100px">   
    
        - 회원가입 기능 구현 
        - 지도 뷰 전체 구현(GoogleMap API마커표시, 서버통신) 
        - 작성 뷰 날짜 선택 기능, 입력받은 장소의 위치정보 계산 구현, 서버 통신 구현  
        - 리스트 뷰 상세 정보 수정 기능 구현  
        - 설정 뷰 계정 로그아웃 기능 구현
    
    - 한현빈  
    <img src = "https://user-images.githubusercontent.com/50194490/113519104-2104f100-95c5-11eb-8ba4-be8eeef73498.png" width = "100px">  
      
        - 로그인 기능 구현
        - 작성 뷰 갤러리에서 이미지 불러오는 기능 구현
        - 리스트 뷰 구현, 삭제 기능 구현 
        - 설정 뷰 계정 삭제 기능 구현

  - BackEnd
    - 손예지  
    <img src = "https://user-images.githubusercontent.com/50194490/113519861-eb163b80-95c9-11eb-88ff-d9da71ffad2c.png" width = "100px">
    
        - DB 설계 및 구축
        - 서버 구축 & API 구현
        - 전반적인 서버관리
        - [MMAP-SERVER](https://github.com/MEMORY-MAP-BY-TTUKDDAK/MMAP-SERVER2 "MemoryMap")


# ❤️ View of Memory Map
- HomeView   
  <img src = "https://user-images.githubusercontent.com/50194490/112957299-42f11480-917c-11eb-8894-bc0cfe1036e0.jpg" width="187.5px"> <img src = "https://user-images.githubusercontent.com/50194490/112957310-45536e80-917c-11eb-9f13-70c59d68f530.jpg" width = "187.5px">

- MapView  
  <img src = "https://user-images.githubusercontent.com/50194490/113518795-7213e580-95c3-11eb-97fc-5107d81725fe.jpg" width = "187.5px">  

- EditView    
  <img src = "https://user-images.githubusercontent.com/50194490/113518865-dd5db780-95c3-11eb-9d07-c36a898f1c72.jpg" width = "187.5px">  

- ListView   
<img src ="https://user-images.githubusercontent.com/50194490/113519801-8bb82b80-95c9-11eb-8450-22ee99a552af.jpg" width = "187.5px"> <img src = "https://user-images.githubusercontent.com/50194490/113518912-30d00580-95c4-11eb-9319-4bfa6d7f6e7e.jpg" width = "187.5px">  <img src = "https://user-images.githubusercontent.com/50194490/113518928-434a3f00-95c4-11eb-817c-68c97ba24385.jpg" width = "187.5px">  
  
- SettingView    
  <img src = "https://user-images.githubusercontent.com/50194490/113519836-c1f5ab00-95c9-11eb-93e5-d495bd1c4b21.png" width = "187.5px">

    

# Function of MemoryMap
- Home View  
  - 로그인 기능
  - 회원가입 기능
- Map View  
  - 마커 표시 기능
  - 마커 터치시 날짜  위치정보 표시 기능
- Edit View  
  - 날짜 선택 기능
  - 사진 첨부 기능
  - 텍스트 작성 기능
  - 위치 입력 기능
- List View
  - 리스트 형태의 기록 표시 기능
  - 기록의 상세정보 확인 기능
  - 기록 수정 기능
  - 기록 삭제 기능
- Setting View
  - 로그아웃 기능
  - 계정 삭제 기능  

# API & Library
- Retrofit2 
- GoogleMapAPI
- Gson 
- Glide
- Geocoder
- RecyclerView
- CardView
- BottomNavigationView
- AppBarLayout
- SwipeRefreshLayout
- DatePicker
