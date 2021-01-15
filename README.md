<img style="border: 0px solid black" src="https://user-images.githubusercontent.com/57944153/104564241-05f1a780-568e-11eb-9659-8a578cb19db4.jpeg" width="200px" />|<img style="border: 0px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/57944153/104564396-32a5bf00-568e-11eb-83a9-9628a933dcc0.jpeg" width="200px" height = "200px" />|<img style="border: 0px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/57944153/104564499-523ce780-568e-11eb-92b0-a5c71229bc62.jpeg" width="200px" height = "200px" />

![Android](C:\Users\thdgn\AndroidStudioProjects\Cherish\image\Android.png)

## 🍒 Project

* <b> SOPT 27th APPJAM, Cherish </b>
* 프로젝트 기간: 2020.12.26 ~ 2021.01.16
> 당신의 소중한 사람들을 위한 연락관리 서비스, CHERISH.

<img style="border: 0px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/63224278/103210113-1bee3100-4948-11eb-9e21-9d41150e854b.png" width="256px" height = "256px" />



## 🛠 개발 환경 및 사용한 라이브러리 (Development Environment and Using Library)

### Development Environment

Android studio


### Using Library  
| 라이브러리(Library) | 목적(Purpose) |
|:---:|:----------:|
| Material design | 바텀 시트 처리 |
| Retrofit | 서버 통신 |
| Glide | 이미지 처리 |
| Material calender | 캘린더 뷰 만들기 |
| Lottie | 애니메이션 처리 |
| Dexter | 퍼미션 처리 |
| ViewModel | MVVM 패턴 사용 |
| LiveData | MVVM 패턴 사용 |
| KTX | JETPACK을 좀 더 편하게 사용하기 위해 |
| Coroutine | 비동기 작업 |
| Flex_box layout | 레이아웃 처리 |


### 📱 Device
- Galaxy - s20

<br>
 <br>

## 📜 Coding Convention 

### 📂 폴더구조

* **java**

  * com.sopt.cherish
    * di
    * remote
      * api
      * singleton
    * repository
    * ui
      * adapter
      * detail
      * dialog
      * domain
      * enrollment
      * factory
      * main
      * notification
      * review
      * signin
      * signup
      * splash
      * tutorial
    * util
      * extension
    * view.calendar

* **res**

  * drawable

  * font

  * layout

  * menu

  * values

<br>

> 아래 사진은 package 분류 이미지

<img width="265" alt="Sources" src="https://user-images.githubusercontent.com/57944153/104696447-a0ff8580-5751-11eb-904b-bfb2d971f4ff.png" align="left">

### ⚙️ 폴더링 규칙

- 폴더링 한 후 com.sopt.cherish 폴더에 있는 파일들은 각 파일 하위에 자신 스토리보드 이름에 해당하는 폴더를 만들어 관리합니다. 

- 파일 네이밍 시, 접두에 스토리보드이름을 붙여서 네이밍합니다.
    - (ex. 스토리보드 이름이 Main, Watering이라고 가정했을 때 class 파일 생성 시 MainBlahblahActivity, WateringBlahblahFragment와 같이 네이밍합니다.)
    
      
    

### 🖋 네이밍

**Class & Struct**

- 클래스/구조체 이름은 **UpperCamelCase**를 사용합니다.

- 클래스 이름에는 접두사를 붙이지 않습니다.

 좋은 예 >

  ```swift
  class CherishActivity: AppCompatActivity()
  ```

 나쁜 예 >

  ```swift
  struct cherishInfo { }
  ```

**함수 & 변수 & 상수**

- 함수와 변수에는 **lowerCamelCase**를 사용합니다.

- 버튼명에는 **Btn 약자**를 사용합니다.

- 모든 IBOutlet에는 해당 클래스명을 뒤에 붙입니다. 
  
    - ~~ImageView, ~~Label, ~~TextField와 같이 속성값을 붙여줍니다.
    
      
    

### 🏷 주석

- `// MARK:` 를 사용해서 연관된 코드를 구분짓습니다.
- `/** */` 를 사용해서 코드 작성자의 이름을 명시합니다.
<br>

### 📎 기타

- 메인컬러와 같이 자주 쓰이는 컬러들은 values 폴더에 Color Set을 만들어서 사용합니다.
- , 뒤에 반드시 띄어쓰기를 합니다.
- 함수끼리 1줄 개행합니다.
- 중괄호는 아래와 같은 형식으로 사용합니다.
```swift
if (condition){

  Statements
  /*
  ...
  */
  
}
```


<br>
<br>

## ✉️ Commit Messge Rules
**안드체리** 들의  **Git Commit Message Rules**

- 반영사항을 바로 확인할 수 있도록 작은 기능 하나라도 구현되면 커밋을 권장합니다.
- 커밋할 땐 **Android 슬랙에 노티**합니다.
- 기능 구현이 완벽하지 않을 땐, 각자 브랜치에 커밋을 해주세요.
<br>

### 📜 커밋 메시지 명령어 모음

```
- feat    : 기능 (새로운 기능)
- fix     : 버그 (버그 수정)
- refactor: 리팩토링
- style   : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
- docs    : 문서 (문서 추가, 수정, 삭제)
- test    : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
- chore   : 기타 변경사항 (빌드 스크립트 수정 등)
```
<br>

### ℹ️ 커밋 메세지 형식
  - `[커밋메세지] 설명` 형식으로 커밋 메시지를 작성합니다.
  - 커밋 메시지는 영어 사용을 권장합니다.

좋은 예 > 

```
  [Feat] fetchcontacts
```

나쁜 예 >
```
  연락처 동기화 기능 추가
```


<br>
<br>

 ## 💻 Github mangement

**안드체리** 들의  WorkFlow : **Gitflow Workflow**

- Master와 Develop 브랜치

  마스터(master): 마스터 브랜치

  개발(develop): 기능들의 통합 브랜치 역할❗️ 이 브랜치에서 기능별로 브랜치를 따 모든 구현이 이루어집니다.

- Master에 직접적인 commit, push는 가급적 금지합니다. (X)

- 커밋 메세지는 다른 사람들이 봐도 이해할 수 있게 써주세요.

- 풀리퀘스트를 통해 코드 리뷰를 해보아요.



<img src="https://camo.githubusercontent.com/5af55d4c184cd61dabf0747bbf9ebc83b358eccb/68747470733a2f2f7761632d63646e2e61746c61737369616e2e636f6d2f64616d2f6a63723a62353235396363652d363234352d343966322d623839622d3938373166396565336661342f30332532302832292e7376673f63646e56657273696f6e3d393133" width="80%">  

   ```
- Master
        ├── dev (Develop)
             ├── HomeTV(각 Local Branch)
             ├── CherishMain    
             └── CherishWatering@@@
   ```
**각자 자신이 맡은 기능 구현에 성공시! 브랜치 다 쓰고 병합하는 방법**

- 브랜치 만듦

```bash
git branch feature/기능이름
```

- 브랜치 전환

```bash
git checkout feature/기능이름
```

- 코드 변경 (현재 **feature/기능이름** 브랜치)

```bash
git add .
git commit -m "커밋 메세지" -a // 이슈보드 이름대로 커밋
```

- 푸시 (현재 **feature/기능이름** 브랜치)

```bash
git push origin feature/기능이름 브랜치
```

- feature/기능 이름 브랜치에서 할 일 다 헀으면 **develop** 브랜치로 전환

```bash
git checkout develop
```

- 머지 (현재 **develop** 브랜치)

```bash
git merge origin feature/기능이름
```

- 다 쓴 브랜치 삭제 (local) (현재 **develop** 브랜치)

```bash
git branch -d feature/기능이름
```

- 다 쓴 브랜치 삭제 (remote) (현재 **develop** 브랜치)

```bash
git push origin :feature/기능이름
```

- develop pull (현재 **develop** 브랜치)

```bash
git pull origin develop
```

- develop push (현재 **develop** 브랜치)

```bash
git push origin develop
```

<br>

## 🏆 기술 스택

- MVVM 패턴 구성
  - ViewModel
  - LiveData
  - DataBinding
  - repository
- 비동기 작업
  - coroutine
- Jetpack 사용 편이
  - Android KTX
- URL 이미지 처리
  - Glide
- 서버통신
  - Retrofit
- 애니메이션 재생
  - Lottie
- 푸시 알람 구현
  - Firebase/Firebase Cloud Messaging
- 퍼미션 처리
  - Dexter
- 항목별 요소 크기별 정렬
  - Flex-box layout



## ⚙️핵심 기능 구현 코드 및 방법 정리

- main view의  bottom_sheet

  <img style="border: 0px solid black" src="https://user-images.githubusercontent.com/57944153/104712080-820aee80-5765-11eb-876d-87085ba52fc4.png" align="left"/>

  - ```kotlin
    private fun initializeBottomSheetBehavior() {
            standardBottomSheetBehavior =
                BottomSheetBehavior.from(binding.homeStandardBottomSheet)
            // bottom sheet state 지정
            standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            standardBottomSheetBehavior.peekHeight = 60.dp
            standardBottomSheetBehavior.expandedOffset = 100.dp
            standardBottomSheetBehavior.halfExpandedRatio = 0.23f
            standardBottomSheetBehavior.isHideable = false
            binding.homeFragment.setBackgroundColor(
                // 하드코딩
                ContextCompat.getColor(
                    requireContext(),
                    R.color.cherish_purple
                )
            )
            standardBottomSheetBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
    
                }
    
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                     transitionBottomSheetParentView(slideOffset)
                }
            })
        }
    ```

  ```
  - material design을 활용하여 persistent bottom sheet를 구현하였습니다. 슬라이드 될 때 배경 색이 모달 처리되도록 하였고, peekHeight와 expandedOffset 속성을 통해 기본으로 보이는 바텀시트의 높이와 확장된 높이를 지정하였습니다.
  ```

  

- 연락처 동기화

  <img style="border: 0px solid black" src="https://user-images.githubusercontent.com/57944153/104712242-ab2b7f00-5765-11eb-9f0a-8b512bf2427e.png" align="left"/>

  - ```kotlin
    fun setSearchListener() {
           binding.editSearch.addTextChangedListener(
               object : TextWatcher {
                   override fun afterTextChanged(s: Editable?) {}
                   override fun beforeTextChanged(
                       s: CharSequence?,
                       start: Int,
                       count: Int,
                       after: Int
                   ) {
                   }
                   override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                       searchText = s.toString()
                       changeList()
                   }
               })
       }
      
       fun changeList() {
           val newList = getPhoneNumbers(sortText, searchText)
           this.phonelist.clear()
           this.phonelist.addAll(newList)
           this.madapter.notifyDataSetChanged()
      
       }
    ```


        fun setList() {
            phonelist.distinct()
            phonelist.addAll(getPhoneNumbers(sortText, searchText))
            phonelist.distinct()
            madapter = PhoneBookAdapter(phonelist)
            binding.recycler.adapter = madapter
            binding.recycler.layoutManager = LinearLayoutManager(context)
        }


​    

        fun getPhoneNumbers(sort: String, search: String): List<Phone> {
            val list = mutableListOf<Phone>()
            val phonUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            // 2.1 전화번호에서 가져올 컬럼 정의
            val projections = arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            // 2.2 조건 정의
            var where: String? = null
            var where2: String? = null


​    

            var whereValues: Array<String>? = null
            // searchName에 값이 있을 때만 검색을 사용한다
            if (search.isNotEmpty()) {


​    

                where = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ? "
        
                where2 = ContactsContract.CommonDataKinds.Phone.NUMBER + " like ?"
        
                whereValues = arrayOf("%$search%")


​    

            }
        
            val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"
        
            context?.run {


​    

                val cursor = contentResolver.query(phonUri, projections, where, whereValues, optionSort)
                while (cursor?.moveToNext() == true) {
                    val id = cursor?.getString(0)
                    val name = cursor?.getString(1)
                    val number = cursor?.getString(2)
        
                    val phone = Phone(id, name, number)
        
                    list.add(phone)
                    list.distinct()
        
                }
        
                val cursor2 =
                    contentResolver.query(phonUri, projections, where2, whereValues, optionSort)
                while (cursor2?.moveToNext() == true) {
                    val id = cursor2?.getString(0)
                    val name = cursor2?.getString(1)
                    val number = cursor2?.getString(2)
        
                    val phone = Phone(id, name, number)
        
                    list.add(phone)
                    list.distinct()
                }
        
            }
        
            // 결과목록 반환
        
            return list.distinct()
        }
    
    ```
  - 사용자 폰에 있는 연락처에 퍼미션을 주어 전화번호부 목록을 전부 가져온다.  사용자의 이름과 번호만 불러와서 리사이클러뷰에 연결해 주고  또한 사용자가 친구의 이름 또는 번호 입력을 통해 연락처를 검색할 수 있도록  db 명령어를 이용해 데이터를 조회하였다.
    
    ```
    
    ```



- 캘린더

  <img style="border: 0px solid black" src="https://user-images.githubusercontent.com/57944153/104710531-8afac080-5763-11eb-92f5-1cd5b8c4b41e.png" width="200px" align="left"/>

  - ```kotlin
    class CalendarFragment : Fragment() {
        private val viewModel: DetailPlantViewModel by activityViewModels()
    
        // keyboard Boolean 디자이너와 얘기 끝나면 제대로 정해서 할 예정
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            setHasOptionsMenu(true)
            val binding: FragmentCalendarBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
    
            // viewModel 작업
            viewModel.fetchCalendarData()
            binding.detailPlantViewModel = viewModel
            initializeCalendar(binding)
    
    
            return binding.root
        }
    
        override fun onResume() {
            super.onResume()
            val activity = activity
            if (activity != null) {
                (activity as DetailPlantActivity).setActionBarTitle("식물 캘린더")
    
            }
        }
    
        override fun onPrepareOptionsMenu(menu: Menu) {
            menu.getItem(0).isVisible = false //disable menuitem 5
            menu.getItem(1).isVisible = false // invisible menuitem 2
            menu.getItem(2).isVisible = false // invisible menuitem 2
    
            (activity as DetailPlantActivity).invalidateOptionsMenu()
    
        }
    
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            when (id) {
                android.R.id.home -> {
                    activity?.onBackPressed()
                    return true
                }
    
            }
            return super.onOptionsItemSelected(item)
        }
    
        private fun initializeCalendar(binding: FragmentCalendarBinding) {
            allowCalendarCache(binding)
            takeNotes(binding)
            addDecorator(binding)
            addDateClickListener(binding)
        }
    
        private fun allowCalendarCache(binding: FragmentCalendarBinding) {
            binding.calendarView.state().edit().isCacheCalendarPositionEnabled(true)
        }
    
        private fun addDecorator(binding: FragmentCalendarBinding) {
            val colorPinkSub = ContextCompat.getColor(requireContext(), R.color.cherish_green_sub)
            val colorGreenSub = ContextCompat.getColor(requireContext(), R.color.cherish_pink_sub)
            viewModel.calendarData.observe(viewLifecycleOwner) {
                binding.calendarView.addDecorator(
                    DotDecorator(
                        colorPinkSub,
                        DateUtil.convertDateToCalendarDay(it.waterData.futureWaterDate)
                    )
                )
                it.waterData.calendarData.forEach {
                    binding.calendarView.addDecorator(
                        DotDecorator(
                            colorGreenSub,
                            DateUtil.convertDateToCalendarDay(it.wateredDate)
                        )
                    )
                }
            }
        }
    
        private fun takeNotes(binding: FragmentCalendarBinding) {
            // 메모를 할 수 있게하다 라는 뜻을 원하는데 마땅히 떠오르는게 없어서 일단 이케 적음
            binding.calendarViewMemoCreateBtn.setOnClickListener { view ->
                binding.calendarView.changeCalendarModeMonths()
            }
            binding.reviewBack.setOnClickListener { view ->
                // 클릭 시 화살표의 모양이 왔다갔다 하면서 바뀌도록 하면 됨
                // review button 이 눌림에 따라
                // textview의 ellipsize 와 maxLine의 수를 바꿔주면 된다.
                binding.calendarView.changeCalendarModeWeeks()
            }
        }
    
        private fun addDateClickListener(binding: FragmentCalendarBinding) {
            binding.calendarView.setOnDateChangedListener { widget, date, selected ->
                showDate(binding, date)
                binding.calendarViewChipLayout.clearChips()
                viewModel.calendarData.observe(viewLifecycleOwner) {
                    val wateredDayList = mutableListOf<CalendarDay?>()
                    val waterDayMemoList = mutableListOf<String?>()
                    val waterDayChipList = mutableListOf<List<String?>>()
                    it.waterData.calendarData.forEach {
                        wateredDayList.add(DateUtil.convertDateToCalendarDay(it.wateredDate))
                        waterDayMemoList.add(it.review)
                        waterDayChipList.add(listOf(it.userStatus1, it.userStatus2, it.userStatus3))
                    }
                    // 함수화 해야합니다.
                    for (i in 0 until wateredDayList.size) {
                        if (wateredDayList[i] == date) {
                            waterDayMemoList[i]?.let { it1 -> showMemo(binding, it1) }
                            showChips(binding, waterDayChipList[i])
                            break
                        } else {
                            binding.reviewAllText.text = " "
                        }
                    }
                }
            }
        }
    
        @SuppressLint("SetTextI18n")
        private fun showDate(binding: FragmentCalendarBinding, date: CalendarDay) {
            binding.calendarViewSelectedDate.text = "${date.year}년 ${date.month}월 ${date.day}일"
        }
    
        private fun showChips(binding: FragmentCalendarBinding, wateredChip: List<String?>) {
            binding.calendarViewChipLayout.clearChips()
            wateredChip.forEach {
                it?.let { it1 -> binding.calendarViewChipLayout.addChip(it1) }
            }
        }
    
        private fun showMemo(binding: FragmentCalendarBinding, waterMemo: String) {
            binding.reviewAllText.text = " "
            binding.reviewAllText.text = waterMemo
        }
    }
    
class CherishMaterialCalendarView constructor(context: Context, attrs: AttributeSet) :
        MaterialCalendarView(context, attrs) {
    
        // todo :애뮬 상에서는 캘린더 줄어드는게 괜찮은데 , 실 기기에서는 잘 안줄어들음
        fun changeCalendarModeWeeks() {
            CoroutineScope(Dispatchers.Main).launch {
                state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
                // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
                layoutParams.height = 150.dp
            }
        }
    
        // todo : 실기기로 할 때 dp값 수정해야함 , 좀 작은 느낌임
        fun changeCalendarModeMonths() {
            CoroutineScope(Dispatchers.Main).launch {
                state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
                // dp를 설정해서 하는건 그닥 좋지 못한 코딩방법
                layoutParams.height = 372.dp
            }
        }
    }
    
    ```
    
    - 기존의 라이브러리인 MaterialCalendarView를 상속받은 CherishCalendarView를 만들어 주간으로 보이는 달력과 개월 간격으로 보이는 달력으로 변환될 수 있도록 함수를 만들어 사용했습니다.


## ⚙️기타 등등

👉🏻 [회의록 링크](https://www.notion.so/Cherish-Android-cec7884792b440d0b3c324d048e55aa6)

👉🏻A-2의 5번(별도의 Layout 사용)

- FlexboxLayout ?  웹에서 사용되던 CSS Flexible Box Layout Module을 안드로이드에 접목하여 개발한 라이브러리이다.  (구글에서 만든 라이브러리)
- 사용한 이유?  앱내에서 EditText 앞에 Chip이 추가되는 방식의 화면을 구현해야 했고 기존의 방식은  ChipGroup과 EditText를 사용해서 입력을 하면 ChipGroup에 추가되는 방식이었지만, 마지막 Chip 바로 옆에 입력창이 있었으면 좋겠다고 생각하여 FlexBoxlayout을 사용하게 되었습니다.



## 👨‍👧‍👧역할 분담

- 송훈기

  물주기 flow, 캘린더 , 초기 프로젝트 세팅, 사용할 기술 스택 레퍼런스 제공, 메인뷰 서버연결

- 안나영

  식물등록 flow, 식물상세뷰, 연락처 동기화, 리드미 작성, 각자 맡은뷰 서버연결

- 권예진

  메인뷰, 마이페이지 뷰, 리드미 작성, modal bottom sheet, 각자 맡은뷰 서버연결




## 🌱 Android Developer

<img style="border: 0px solid black" src="https://user-images.githubusercontent.com/57944153/104564241-05f1a780-568e-11eb-9659-8a578cb19db4.jpeg" width="200px" /> | <img style="border: 0px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/57944153/104564396-32a5bf00-568e-11eb-83a9-9628a933dcc0.jpeg" width="200px" height = "200px" />| <img style="border: 0px solid black !important; border-radius:20px; " src="https://user-images.githubusercontent.com/57944153/104564499-523ce780-568e-11eb-92b0-a5c71229bc62.jpeg" width="200px" height = "200px" />

​					[훈기](https://github.com/SSong-develop) 										[나영](https://github.com/ny2060) 											[예진](https://github.com/YEJIN-LILY)

