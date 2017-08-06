package com.mayinews.mv.home.bean;

/**
 * Created by gary on 2017/7/3 0003.
 * 微信:GH-12-10
 * QQ:1683701403
 */

public class ItemVideoBean {


    /**
     * data : {"PlayAuth":"eyJTZWN1cml0eVRva2VuIjoiQ0FJU3VBSjFxNkZ0NUIyeWZTaklyTG5nRW9MQ2w1Umk0cWVTUUZmNnJEZ2dmY0ZOM0ozYW9UejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRjhVVkdCT2FONU1vdE0wTnIxTDdKcExGc3QycDZyOEpqc1VKNCs5RnMxMnBzdlhKYXNEVkVmbkFHSjcwR1VpbSt3WjN4YnpsRHh2QU8zV3VMWnlPajdOK2M5MFRSWFBXUkRGYUJkQlFWRTBBenNvR0xpbnBNZis4UHdURG4zYlFiaTV0cGhFdXNXZDIrSVc1ek1yK2pCL0Nsdy9XeCtRUHRwenRBWlcxRmI0T1dxMXlTTkNveHVkN1c3UGMyU3BMa1hodytieHhrYlpQOUVXczNMamZJU0VJczByZGJiV0VyNFV4ZFZVbFB2VmxJY01lOHFpZ3o4OGZrL2ZJaW9INnh5eEtPZXhvU0NuRlRPaWl1cENlUnI3M2FJNXBKT3VsWUNxVmc0bmZiSU9KbWdjbGNHOGRDZ1JHUU5Nb01VUnNFaVlyVGp6SzJ3RlFQcVFob0d3YWdBRWZhVGphZ1ZzU3ZNaFN5WXVrd0dWK283bGZ4UHFyYXFnUTR6Tm5kYzF4azE3WnBySVlMN0xESUora21IWlo1dGFpUUNsbG02d0s1b2g1OFpWeWlQUFUwb3E1VXlTaThSNTR0RWtVZmFKZ1ZaaU1RWGxoeC91c2twL01oS0dPUFl4b3c3YU5lR2hOZ0xOUFNWRlIyb2tKeHpJVEZnY1FJK0RYemh5dkU3NnJTdz09IiwiQXV0aEluZm8iOiJ7XCJFeHBpcmVUaW1lXCI6XCIyMDE3LTA3LTAzVDA5OjMzOjM3WlwiLFwiTWVkaWFJZFwiOlwiMTYwNDJiMWRmMDEyNDE2MjgyN2U4YzIzNmU0MjM5M2VcIixcIlNpZ25hdHVyZVwiOlwiRm9aZG0xOGxBbWtGZFhvck9HR0NOL1FFRkw0PVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiMTYwNDJiMWRmMDEyNDE2MjgyN2U4YzIzNmU0MjM5M2UiLCJUaXRsZSI6IuS4ieS9jeaooeS7v+iAheS4juW8oOWtpuWPi+WQjOWPsOeMruWUse+8jOatjOelnuS4gOW8gOWPo+WPsOS4i+ayuOiFvuS6hu+8gSIsIkNvdmVyVVJMIjoiaHR0cDovL3ZpZGVvLm1heWluZXdzLmNvbS9zbmFwc2hvdC8xNjA0MmIxZGYwMTI0MTYyODI3ZThjMjM2ZTQyMzkzZTAwMDA3LmpwZz9hdXRoX2tleT0xNDk5MDc3OTE3LTAtMC03ZGEyMWJkOTkyMzY2ZDg4NDgyMDU2NmQ5MDBhYWM5MiIsIkR1cmF0aW9uIjoxOTcuMjF9LCJBY2Nlc3NLZXlJZCI6IlNUUy5NelVZOHZ6S0NVZXlCcUtIOHVxTmEzWHFDIiwiUGxheURvbWFpbiI6InZpZGVvLm1heWluZXdzLmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjZhTGR4a1NvQ0tnQ0hpZ3VYdGQ4cjUxbURTdHdFcTdVbVNjSFhLTnJXc0QzIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNDI4NTc2MjAwMjEyNjkyfQ==","RequestId":"A577EAD0-0722-4301-924E-256B3E4F8CDD","VideoMeta":{"CoverURL":"http://video.mayinews.com/snapshot/16042b1df0124162827e8c236e42393e00007.jpg?auth_key=1499077917-0-0-7da21bd992366d884820566d900aac92","Duration":197.21000671387,"Status":"Normal","Title":"三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！","VideoId":"16042b1df0124162827e8c236e42393e"}}
     * status : 1
     */

    private DataBean data;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * PlayAuth : eyJTZWN1cml0eVRva2VuIjoiQ0FJU3VBSjFxNkZ0NUIyeWZTaklyTG5nRW9MQ2w1Umk0cWVTUUZmNnJEZ2dmY0ZOM0ozYW9UejJJSHBLZVhkdUFlQVhzL28wbW1oWjcvWVlsck1xRjhVVkdCT2FONU1vdE0wTnIxTDdKcExGc3QycDZyOEpqc1VKNCs5RnMxMnBzdlhKYXNEVkVmbkFHSjcwR1VpbSt3WjN4YnpsRHh2QU8zV3VMWnlPajdOK2M5MFRSWFBXUkRGYUJkQlFWRTBBenNvR0xpbnBNZis4UHdURG4zYlFiaTV0cGhFdXNXZDIrSVc1ek1yK2pCL0Nsdy9XeCtRUHRwenRBWlcxRmI0T1dxMXlTTkNveHVkN1c3UGMyU3BMa1hodytieHhrYlpQOUVXczNMamZJU0VJczByZGJiV0VyNFV4ZFZVbFB2VmxJY01lOHFpZ3o4OGZrL2ZJaW9INnh5eEtPZXhvU0NuRlRPaWl1cENlUnI3M2FJNXBKT3VsWUNxVmc0bmZiSU9KbWdjbGNHOGRDZ1JHUU5Nb01VUnNFaVlyVGp6SzJ3RlFQcVFob0d3YWdBRWZhVGphZ1ZzU3ZNaFN5WXVrd0dWK283bGZ4UHFyYXFnUTR6Tm5kYzF4azE3WnBySVlMN0xESUora21IWlo1dGFpUUNsbG02d0s1b2g1OFpWeWlQUFUwb3E1VXlTaThSNTR0RWtVZmFKZ1ZaaU1RWGxoeC91c2twL01oS0dPUFl4b3c3YU5lR2hOZ0xOUFNWRlIyb2tKeHpJVEZnY1FJK0RYemh5dkU3NnJTdz09IiwiQXV0aEluZm8iOiJ7XCJFeHBpcmVUaW1lXCI6XCIyMDE3LTA3LTAzVDA5OjMzOjM3WlwiLFwiTWVkaWFJZFwiOlwiMTYwNDJiMWRmMDEyNDE2MjgyN2U4YzIzNmU0MjM5M2VcIixcIlNpZ25hdHVyZVwiOlwiRm9aZG0xOGxBbWtGZFhvck9HR0NOL1FFRkw0PVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiMTYwNDJiMWRmMDEyNDE2MjgyN2U4YzIzNmU0MjM5M2UiLCJUaXRsZSI6IuS4ieS9jeaooeS7v+iAheS4juW8oOWtpuWPi+WQjOWPsOeMruWUse+8jOatjOelnuS4gOW8gOWPo+WPsOS4i+ayuOiFvuS6hu+8gSIsIkNvdmVyVVJMIjoiaHR0cDovL3ZpZGVvLm1heWluZXdzLmNvbS9zbmFwc2hvdC8xNjA0MmIxZGYwMTI0MTYyODI3ZThjMjM2ZTQyMzkzZTAwMDA3LmpwZz9hdXRoX2tleT0xNDk5MDc3OTE3LTAtMC03ZGEyMWJkOTkyMzY2ZDg4NDgyMDU2NmQ5MDBhYWM5MiIsIkR1cmF0aW9uIjoxOTcuMjF9LCJBY2Nlc3NLZXlJZCI6IlNUUy5NelVZOHZ6S0NVZXlCcUtIOHVxTmEzWHFDIiwiUGxheURvbWFpbiI6InZpZGVvLm1heWluZXdzLmNvbSIsIkFjY2Vzc0tleVNlY3JldCI6IjZhTGR4a1NvQ0tnQ0hpZ3VYdGQ4cjUxbURTdHdFcTdVbVNjSFhLTnJXc0QzIiwiUmVnaW9uIjoiY24tc2hhbmdoYWkiLCJDdXN0b21lcklkIjoxNDI4NTc2MjAwMjEyNjkyfQ==
         * RequestId : A577EAD0-0722-4301-924E-256B3E4F8CDD
         * VideoMeta : {"CoverURL":"http://video.mayinews.com/snapshot/16042b1df0124162827e8c236e42393e00007.jpg?auth_key=1499077917-0-0-7da21bd992366d884820566d900aac92","Duration":197.21000671387,"Status":"Normal","Title":"三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！","VideoId":"16042b1df0124162827e8c236e42393e"}
         */

        private String PlayAuth;
        private String RequestId;
        private VideoMetaBean VideoMeta;

        public String getPlayAuth() {
            return PlayAuth;
        }

        public void setPlayAuth(String PlayAuth) {
            this.PlayAuth = PlayAuth;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String RequestId) {
            this.RequestId = RequestId;
        }

        public VideoMetaBean getVideoMeta() {
            return VideoMeta;
        }

        public void setVideoMeta(VideoMetaBean VideoMeta) {
            this.VideoMeta = VideoMeta;
        }

        public static class VideoMetaBean {
            /**
             * CoverURL : http://video.mayinews.com/snapshot/16042b1df0124162827e8c236e42393e00007.jpg?auth_key=1499077917-0-0-7da21bd992366d884820566d900aac92
             * Duration : 197.21000671387
             * Status : Normal
             * Title : 三位模仿者与张学友同台献唱，歌神一开口台下沸腾了！
             * VideoId : 16042b1df0124162827e8c236e42393e
             */

            private String CoverURL;
            private double Duration;
            private String Status;
            private String Title;
            private String VideoId;

            public String getCoverURL() {
                return CoverURL;
            }

            public void setCoverURL(String CoverURL) {
                this.CoverURL = CoverURL;
            }

            public double getDuration() {
                return Duration;
            }

            public void setDuration(double Duration) {
                this.Duration = Duration;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getVideoId() {
                return VideoId;
            }

            public void setVideoId(String VideoId) {
                this.VideoId = VideoId;
            }
        }
    }
}
